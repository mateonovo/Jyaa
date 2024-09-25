package api;

import java.util.List;


import javax.persistence.PersistenceException;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;

import bd.Insumo;
import dao.InsumoDAO;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/insumos")
public class InsumosController {
	
	@Inject
	private InsumoDAO insumoDao;
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener un insumo por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Insumo encontrado",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Insumo.class))),
	    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
	})
    public Response getInsumoById(@Parameter(description = "ID del insumo", required = true) @PathParam("id") int id) {
    	Insumo insumo = insumoDao.findActiveById(id);
        if (insumo == null) {
        	String mensaje= "No se encontró el insumo con id: " + id;
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(insumo).build();
    }
	
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todos los insumos")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Insumos encontrados"),
	    @ApiResponse(responseCode = "404", description = "Insumos no encontrados")
	})
    public Response getAllInsumos() {
    	List<Insumo> insumos = insumoDao.findAllActives();
        if (insumos == null) {
        	String mensaje = new JSONObject().put("message", "Insumos no encontrados").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(insumos).build();
    }
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Creacion de un nuevo insumo")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Insumo creado",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Insumo.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
    public Response createInsumo(@Parameter(description = "Datos del insumo", required = true) Insumo insumo) {
		Insumo aux = new Insumo(insumo.getNombre(), insumo.getCantidad(), insumo.getCostoUnitario());
    	try {
        	insumoDao.persist(aux);
    	} catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException) {
            	String mensaje = new JSONObject().put("message", "Ese nombre ya se encuentra registrado").toString();
        		return Response.status(Response.Status.CONFLICT).entity(mensaje).build(); }
            if (cause instanceof PropertyValueException) {
            	String mensaje = new JSONObject().put("message", "Faltan completar campos").toString();
            	return Response.status(Response.Status.CONFLICT).entity(mensaje).build(); }
    }
    	return Response.status(Response.Status.CREATED).entity(insumo).build();
   }
	

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Actualización de un insumo")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Insumo actualizado",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Insumo.class))),
	    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
	})
    public Response updateInsumo(@Parameter(description = "Datos del insumo a actualizar", required = true) Insumo insumo){
    	Insumo aux = insumoDao.findActiveById(insumo.getId());
    	if (aux != null) {
    		insumoDao.update(insumo);
    		return Response.ok().entity(insumo).build();
    	}
	    else {
	    	String mensaje = new JSONObject().put("message", "El insumo no existe").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
    }
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar un insumo por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Insumo eliminado"),
	    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
	})
    public Response deleteInsumo(@Parameter(description = "ID del insumo", required = true) @PathParam("id") Integer id){
    	Insumo aux = insumoDao.findActiveById(id);
    	if (aux != null){
    		aux.setActivo(false);
    		insumoDao.update(aux);
    		String mensaje = new JSONObject().put("message", "Insumo eliminado").toString();
    		return  Response.ok().entity(mensaje).build();
	    } else {
	    	String mensaje = new JSONObject().put("message", "El insumo no existe").toString();
	    	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
	  	}
	}
}
