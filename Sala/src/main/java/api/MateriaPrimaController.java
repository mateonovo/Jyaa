package api;

import jakarta.inject.Inject;



import bd.MateriaPrima;
import jakarta.validation.ConstraintViolationException;
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
import jakarta.ws.rs.core.Response.Status;
import requests.MateriaPrimaRequest;
import dao.FamiliaProductoraDAO;
import dao.MateriaPrimaDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import javax.persistence.PersistenceException;
import org.hibernate.PropertyValueException;
import org.json.JSONObject;

import bd.FamiliaProductora;


@Path("/materiasPrimas")
public class MateriaPrimaController {

	@Inject
	private MateriaPrimaDAO materiaPrimaDAO;
	
	@Inject
	private FamiliaProductoraDAO familiaProductoraDAO;

	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener una materia prima por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "materia prima encontrada",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = MateriaPrima.class))),
	    @ApiResponse(responseCode = "404", description = "materia prima no encontrada")
	})
	public Response getMateriaPrimaById(@Parameter(description = "ID de la materia prima", required = true)@PathParam("id") int id) {
		MateriaPrima materiaPrima = materiaPrimaDAO.findActiveById(id);
		if (materiaPrima == null) {
			String mensaje = "No se encontró la materia prima";
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
		return Response.ok(materiaPrima).build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todas las materias primas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Materias primas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MateriaPrima.class))),
			@ApiResponse(responseCode = "404", description = "Materias primas no encontradas") })
	public Response getAllMateriasPrimas() {
		List<MateriaPrima> materiasPrimas =  materiaPrimaDAO.findAllActives();
		if (materiasPrimas == null) {
			String mensaje = new JSONObject().put("message", "No se encontaron materias primas").toString();
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
		return Response.ok(materiasPrimas).build();
	}
	
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear una materia prima")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Materia prima encontrado",
		        content = @Content(mediaType = "application/json",
		        schema = @Schema(implementation = MateriaPrima.class))),
		    @ApiResponse(responseCode = "404", description = "Materia prima no encontrado")
		})
	public Response crearMateriaPrima(@Parameter(description = "Datos de la nueva materia prima", required = true) MateriaPrimaRequest materiaPrima) {
		String mensaje = "";
		String mensaje1 = new JSONObject().put("message", "El nombre ya se encuentra registrado").toString();
		String mensaje2 = new JSONObject().put("message", "Falta completar campo/s obligatorio/s").toString();
		FamiliaProductora productor = familiaProductoraDAO.getByName(materiaPrima.getNombreProductor());
		
		MateriaPrima materia = new MateriaPrima(materiaPrima.getNombre(), materiaPrima.getPeso()
				,materiaPrima.getFechaCompra(),materiaPrima.getFechaVencimiento(), materiaPrima.getCostoPorKg(), 
				materiaPrima.getFormaAlmacenamiento(), productor);
		
		
		//FamiliaProductora productor = materiaPrima.getProductor();
		if (productor == null || productor.getNombre() == null || productor.getNombre().isEmpty()) {
			mensaje = new JSONObject().put("message", "El nombre del productor es requerido").toString();
			return Response.status(Status.BAD_REQUEST).entity(mensaje).build();
		}
			
		try {
			FamiliaProductora productorEncontrado = familiaProductoraDAO.getByName(productor.getNombre());
			if (productorEncontrado == null) {
				mensaje = new JSONObject().put("message", "El productor no existe").toString();
				return Response.status(Status.NOT_FOUND).entity(mensaje).build();
			}
			materia.setProductor(productorEncontrado);
			materiaPrimaDAO.persist(materia);
			mensaje = new JSONObject().put("message", "Materia prima creada exitosamente").toString();
			return Response.status(Status.CREATED).entity(mensaje).build();
	   	} catch (PersistenceException e) {
	        Throwable cause = e.getCause();
	        if (cause instanceof ConstraintViolationException) 
	        	return Response.status(Response.Status.CONFLICT).entity(mensaje1).build();	
	        if (cause instanceof PropertyValueException) 
	        	return Response.status(Response.Status.CONFLICT).entity(mensaje2).build();
	}
	mensaje = new JSONObject().put("message", "Error interno del servidor").toString();	
	return Response.status(Status.INTERNAL_SERVER_ERROR).entity(mensaje).build();
  }
		

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Eliminar una materia prima")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Materia prima eliminada"),
			@ApiResponse(responseCode = "404", description = "Materia prima no encontrada") })
	public Response deleteMateriaPrima(@Parameter(description = "ID de MateriaPrima", required = true)   @PathParam("id") int id) {
		MateriaPrima materiaPrima = materiaPrimaDAO.findById(id);
		if (materiaPrima == null) {
			String mensaje = "No se encontró la materia prima";
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
		materiaPrima.setActivo(false);
		materiaPrimaDAO.update(materiaPrima);
		return Response.ok(materiaPrima).build();
	}

	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Actualizar una materia prima")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Materia prima actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MateriaPrima.class))),
			@ApiResponse(responseCode = "404", description = "Materia prima no encontrada") })
	public Response updateMateriaPrima(MateriaPrimaRequest materiaPrima,@Parameter(description = "ID de MateriaPrima", required = true)   @PathParam("id") int id) {
		MateriaPrima aux = materiaPrimaDAO.findById(id);
		if (aux != null) {		
			aux.setNombre(materiaPrima.getNombre());
			aux.setCostoPorKg(materiaPrima.getCostoPorKg());
			aux.setPeso(materiaPrima.getPeso());
			aux.setCostoPorKg(materiaPrima.getCostoPorKg());
			aux.setFormaAlmacenamiento(materiaPrima.getFormaAlmacenamiento());
			aux.setFechaCompra(materiaPrima.getFechaCompra());
			aux.setFechaVencimiento(materiaPrima.getFechaVencimiento());		
			materiaPrimaDAO.update(aux);
			return Response.ok().entity(aux).build();
		} else
			return Response.status(Response.Status.NOT_FOUND).entity("La materia prima no existe").build();
	}

}








