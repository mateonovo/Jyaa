package api;

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
import dao.FamiliaProductoraDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;

import bd.FamiliaProductora;


@Path("/familias_productoras")
public class FamiliaProductoraController {

	@Inject
	private FamiliaProductoraDAO fpDao;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener una familia productora por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Familia productora encontrada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = FamiliaProductora.class))),
	    @ApiResponse(responseCode = "404", description = "Familia productora no encontrada")
	})
    public Response getFpById(@Parameter(description = "ID de la Familia productora", required = true) @PathParam("id") int id) {
    	FamiliaProductora fp = fpDao.findActiveById(id);
        if (fp == null) {
        	String mensaje = new JSONObject().put("message", "No se encontró la familia con ese ID").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(fp).build();        
    }
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todas las familias productoras")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Familias Productoras encontradas"),
	    @ApiResponse(responseCode = "404", description = "Familias Productoras no encontradas")
	})
    public Response getAllFamiliasProductoras() {
    	List<FamiliaProductora> familias = fpDao.findAllActives();
        if (familias == null) {
        	String mensaje = new JSONObject().put("message", "Familias Productoras no encontradas").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(familias).build();
    }

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Creacion de una familia productora")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Familia productora creada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = FamiliaProductora.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
    public Response createFamiliaProductora(@Parameter(description = "Datos de la familia productora", required = true) FamiliaProductora fp) {
		FamiliaProductora aux = new FamiliaProductora(fp.getNombre(), fp.getFechaInicio(), fp.getZona());
		//System.out.println("entro");
    	try {
    		//System.out.print("entro");
        	fpDao.persist(aux);
    	} catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException) {
            	String mensaje = new JSONObject().put("message", "El nombre ya se se encuentra registrado").toString();
            	return Response.status(Response.Status.CONFLICT).entity(mensaje).build();	
            }
            if (cause instanceof PropertyValueException) {
            	String mensaje = new JSONObject().put("message", "Los campos no se completaron correctamente").toString();
            	return Response.status(Response.Status.CONFLICT).entity(mensaje).build();}
    }
    	return Response.status(Response.Status.CREATED).entity(fp).build();
   }

	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Actualización de una familia productora")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Familia productora actualizada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = FamiliaProductora.class))),
	    @ApiResponse(responseCode = "404", description = "Familia Productora no encontrada")
	})
    public Response updateFamiliaProductora(@Parameter(description = "Datos de la familia productora a actualizar", required = true) FamiliaProductora fp){
    	FamiliaProductora aux = fpDao.findActiveById(fp.getId());
    	if (aux != null) {
    		fpDao.update(fp);
    		return Response.ok().entity(fp).build();
    	}
	    else {
	    	String mensaje = new JSONObject().put("message", "La familia productora no existe").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
    }
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar una familia productora por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Familia productora eliminada"),
	    @ApiResponse(responseCode = "404", description = "Familia productora no encontrada")
	})
    public Response deleteFamiliaProductora(@Parameter(description = "ID de la familia productora", required = true) @PathParam("id") Integer id){
    	FamiliaProductora aux = fpDao.findActiveById(id);
    	if (aux != null){
    		aux.setActivo(false);
    		fpDao.update(aux);
    		String mensaje = new JSONObject().put("message", "Familia Productora eliminada").toString();
    		return  Response.ok().entity(mensaje).build();
	    } else {
	    	String mensaje = new JSONObject().put("message", "No se encontró la Familia Productora").toString();
	    	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
	  	}
	}
	
}
