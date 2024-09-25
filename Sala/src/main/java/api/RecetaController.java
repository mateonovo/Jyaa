package api;

import java.util.List;

import javax.persistence.PersistenceException;


import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;

import bd.FamiliaProductora;
import bd.Receta;
import bd.Usuario;
import dao.RecetaDAO;
import dao.UsuarioDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import requests.RecetaRequest;

@Path("/recetas")
public class RecetaController {
	
	@Inject
	private RecetaDAO recetaDao;
	
	@Inject
	private UsuarioDAO usuarioDao;
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener una receta por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Receta encontrada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Receta.class))),
	    @ApiResponse(responseCode = "404", description = "Receta no encontrada")
	})
    public Response getRecetaById(@Parameter(description = "ID de la receta", required = true) @PathParam("id") int id) {
    	Receta receta = recetaDao.findActiveById(id);
        if (receta == null) {
        	String mensaje = new JSONObject().put("message", "No se encontró una receta con ese id").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(receta).build();
    }
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todas las recetas")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Recetas encontradas"),
	    @ApiResponse(responseCode = "404", description = "Recetas Productoras no encontradas")
	})
    public Response getAllRecetas() {
    	List<Receta> recetas = recetaDao.findAllActives();
        if (recetas == null) {
        	String mensaje = new JSONObject().put("message", "Recetas no encontradas").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(recetas).build();
    }
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Creacion de una receta")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Receta creada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Receta.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
    public Response createReceta(@Parameter(description = "Datos de la receta", required = true) RecetaRequest receta, @Context ContainerRequestContext requestContext) {
		Usuario user = usuarioDao.findActiveByEmail(requestContext.getProperty("userEmail").toString());
		if (user == null) {
			String mensaje = new JSONObject().put("message", "El usuario especificado no existe").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
		Receta aux = new Receta(receta.getNombre(), receta.getTexto(), user);
		try {
        	recetaDao.persist(aux);
    	} catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException) {
            	String mensaje = new JSONObject().put("message", "Ya existe una receta con ese nombre").toString();
        		return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
            if (cause instanceof PropertyValueException) {
            	String mensaje = new JSONObject().put("message", "No se completaron correctamente solo los campos").toString();
        		return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
    }
    	return Response.status(Response.Status.CREATED).entity(aux).build();
   }
	

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Actualización de una receta")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Receta actualizada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Receta.class))),
	    @ApiResponse(responseCode = "404", description = "Receta no encontrada")
	})
    public Response updateReceta(@Parameter(description = "Datos de la receta a actualizar", required = true) RecetaRequest receta){
    	Receta aux = recetaDao.getByName(receta.getNombre());
    	if (aux != null) {
    		aux.setTexto(receta.getTexto());
    		recetaDao.update(aux);
    		return Response.ok().entity(aux).build();
    	}
	    else {
	    	String mensaje = new JSONObject().put("message", "La receta no existe").toString();
    		return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
    }
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar una receta por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Receta eliminada"),
	    @ApiResponse(responseCode = "404", description = "Receta no encontrada")
	})
    public Response deleteReceta(@Parameter(description = "ID de la receta", required = true) @PathParam("id") Integer id){
    	Receta aux = recetaDao.findActiveById(id);
    	if (aux != null){
    		aux.setActivo(false);
    		recetaDao.update(aux);
    		String mensaje = new JSONObject().put("message", "Receta eliminada").toString();
        	return Response.ok(mensaje).build(); 
	    } else {
	    	String mensaje = new JSONObject().put("message", "La receta no existe").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
	}

}
