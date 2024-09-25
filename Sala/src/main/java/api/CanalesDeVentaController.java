package api;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;

import bd.CanalDeVenta;
import bd.StockProductoTerminado;
import dao.CanalDeVentaDAO;
import dao.StockProductoTerminadoDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import requests.CanalDeVentaRequest;


@Path("/canalesVenta")
public class CanalesDeVentaController {
	
	@Inject
	private CanalDeVentaDAO canalesDeVentaDao;
	
	@Inject
	private StockProductoTerminadoDAO stockDao;

	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener un canal de venta por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Canal de venta encontrado",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = CanalDeVenta.class))),
	    @ApiResponse(responseCode = "404", description = "Canal de venta no encontrado")
	})
	public Response getCanalDeVentaById(@Parameter(description = "ID del canal de ventas", required = true)@PathParam("id") int id) {
		CanalDeVenta canalDeVenta = canalesDeVentaDao.findActiveById(id);
		if (canalDeVenta == null) {
			String mensaje = new JSONObject().put("message", "No se encontró el canal de venta").toString();
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
		return Response.ok(canalDeVenta).build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todos los canales de venta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Canales de venta encontrados"),
			@ApiResponse(responseCode = "404", description = "Canales de venta no encontrados") })
	public Response getAllCanalesDeVenta() {
		List<CanalDeVenta> canales = canalesDeVentaDao.findAllActives();
		if (canales == null) {
			String mensaje = new JSONObject().put("message", "No hay canales de venta disponibles").toString();
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
			
		}
		return Response.ok(canales).build();
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Crear un canal de venta ")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Canal de venta creado correctamente",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = CanalDeVenta.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
	public Response createCanalDeVenta(@Parameter(description = "Datos del nuevo canal", required = true) CanalDeVentaRequest
			canalDeVenta) {
		CanalDeVenta canal = new CanalDeVenta(canalDeVenta.getNombre(), canalDeVenta.getUbicacion());
		String mensaje = new JSONObject().put("message", "El nombre ya se encuentra registrado").toString();
		String mensaje1 = new JSONObject().put("message", "Falta completar campo/s obligatorio/s").toString();
		
		try {
			canalesDeVentaDao.persist(canal);
	   	} catch (PersistenceException e) {
	        Throwable cause = e.getCause();
	        if (cause instanceof ConstraintViolationException) 
	  
	        	return Response.status(Response.Status.CONFLICT).entity(mensaje).build();	
	        if (cause instanceof PropertyValueException) 
	        	return Response.status(Response.Status.CONFLICT).entity(mensaje1).build();
	}
		return Response.status(Response.Status.CREATED).entity(canal).build();
	}
	

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Actualizar un canal de venta ")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Canal de venta actualizado correctamente",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = CanalDeVenta.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
	public Response updateCanalDeVenta(@Parameter(description = "ID del canal de ventas", required = true)@PathParam("id") Integer id,CanalDeVentaRequest canalDeVenta){
	    CanalDeVenta aux = canalesDeVentaDao.findActiveById(id);
	    if (aux != null) {
	    	aux.setNombre(canalDeVenta.getNombre());
	        aux.setUbicacion(canalDeVenta.getUbicacion());
	        canalesDeVentaDao.update(aux);
	        return Response.ok().entity(aux).build();
	    }
	    else {
	    	String mensaje = new JSONObject().put("message", "No se encontró el canal de venta").toString();
	        return Response.status(Status.NOT_FOUND).entity(mensaje).build();
	    }
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar un canal de venta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Canal de venta eliminado"),
			@ApiResponse(responseCode = "404", description = "Canal de venta no encontrado") })
	public Response deleteCanalDeVenta(@Parameter(description = "ID del canal de ventas", required = true)@PathParam("id") Integer id) {
		CanalDeVenta aux = canalesDeVentaDao.findActiveById(id);
		if (aux != null) {
			aux.setActivo(false);
			canalesDeVentaDao.update(aux);
			String mensaje = new JSONObject().put("message", "Canal de venta eliminado").toString();
			return Response.ok().entity(mensaje).build();
		} else {
			String mensaje = new JSONObject().put("message", "Canal de venta no encontrado").toString();
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
	}
	
	@Path("/{productoId}/agregarProducto")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Agregar productos al canal de venta")
	public Response agregarProductos(
	    @Parameter(description = "ID del producto", required = true) @PathParam("productoId") int productoId,
	    @Parameter(description = "ID del canal", required = true) @QueryParam("idCanal") int idCanal,
	    @Parameter(description = "Cantidad de productos", required = true) @QueryParam("cantProductos") int cantProductos){
		StockProductoTerminado producto = stockDao.findActiveById(productoId);
		CanalDeVenta canal = canalesDeVentaDao.findActiveById(idCanal);
		int stockActual = producto.getCantidadProductos();
		if (stockActual < cantProductos) {
			String mensaje = new JSONObject().put("message", "Cantidad de productos insuficiente").toString();
            return Response.status(Response.Status.CONFLICT).entity(mensaje).build();
		}
		producto.setCantidadProductos(stockActual - cantProductos);
		if (stockActual - cantProductos == 0) {
			producto.setActivo(false);
		}
		stockDao.update(producto);
		canal.agregarProductoTerminado(producto);
		canalesDeVentaDao.update(canal);
     	String mensaje = new JSONObject().put("message", "Productos entregados").toString();
		return Response.ok().entity(mensaje).build();
	}
	
	@GET
	@Path("/{id}/productos")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener productos de un canal")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Productos encontrados"),
			@ApiResponse(responseCode = "404", description = "Productos no encontrados") })
	@SecurityRequirement(name = "bearerAuth")
	public Response getProductos(@Parameter(description = "ID del canal de ventas", required = true)@PathParam("id") Integer id) {
		CanalDeVenta canal = canalesDeVentaDao.findActiveById(id);
		List<StockProductoTerminado> productos = canal.getProductos();
		if (productos == null) {
			String mensaje = new JSONObject().put("message", "El canal no posee productos").toString();
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
			
		}
		return Response.ok(productos).build();
	}
	
	
	
	
	
}




















