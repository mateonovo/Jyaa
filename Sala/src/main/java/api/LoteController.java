package api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONObject;

import bd.Insumo;
import bd.ItemDeMateriaPrima;
import bd.Lote;
import bd.MateriaPrima;
import bd.Receta;
import bd.StockProductoTerminado;
import bd.Usuario;
import dao.ItemDeMateriaPrimaDAO;
import dao.LoteDAO;
import dao.MateriaPrimaDAO;
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
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import requests.ItemDeMateriaPrimaRequest;
import requests.LoteRequest;

@Path("/lotes")
public class LoteController {
	
	@Inject
	private LoteDAO loteDao;
	@Inject
	private MateriaPrimaDAO materiaDao;
	@Inject
	private ItemDeMateriaPrimaDAO itemDao;
	@Inject
	private UsuarioDAO userDao;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener todos los lotes")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Lotes encontrados"),
	    @ApiResponse(responseCode = "404", description = "Lotes no encontrados")
	})
    public Response getAllLotes() {
    	List<Lote> lotes = loteDao.findAll();
        if (lotes == null) {
        	String mensaje = new JSONObject().put("message", "No hay lotes disponibles").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(lotes).build();
    }
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Obtener un lote por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Lote encontrado",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Lote.class))),
	    @ApiResponse(responseCode = "404", description = "Lote no encontrado")
	})
    public Response getLoteById(@Parameter(description = "ID del lote", required = true) @PathParam("id") int id) {
    	Lote lote = loteDao.findById(id);
        if (lote == null) {
        	String mensaje= "No se encontró el lote";
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
        }
        return Response.ok(lote).build();
    }
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Creacion de un nuevo lote")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Lote creado correctamente",
	        content = @Content(mediaType = "application/json",
	        schema = @Schema(implementation = Lote.class))),
	    @ApiResponse(responseCode = "409", description = "Conflicto de datos")
	})
    public Response crearLote(@Parameter(description = "Datos del lote", required = true) LoteRequest lote, @Context ContainerRequestContext requestContext) {
		try {
			List<ItemDeMateriaPrima> auxLista = new ArrayList<ItemDeMateriaPrima>();
			MateriaPrima auxMp;
			ItemDeMateriaPrima auxI;
			double suma = 0;
			Lote auxLote = new Lote(lote.getNombre(), lote.getCodigo(), lote.getFechaElaboracion(), lote.getCantidadProducida(), userDao.findActiveByEmail(requestContext.getProperty("userEmail").toString()));
			//loteDao.persist(auxLote);
			if (lote.getItemsDeMateriaPrima() == null || lote.getItemsDeMateriaPrima().size() <= 0) {
				String mensaje = new JSONObject().put("message", "No se seleccionó la materia prima del lote").toString();
            	return Response.status(Response.Status.CONFLICT).entity(mensaje).build();
			}
			for (ItemDeMateriaPrimaRequest item : lote.getItemsDeMateriaPrima()) {
				//System.out.println(item.getMateriaPrimaId() + " - " + item.getCantidadEnKg());
				auxMp = materiaDao.findActiveById(item.getMateriaPrimaId());
				if (item.getCantidadEnKg() > auxMp.getPeso()) {
					String mensaje = new JSONObject().put("message", "Cantidad de materia prima '" + auxMp.getNombre() + "' insuficiente").toString();
	            	return Response.status(Response.Status.CONFLICT).entity(mensaje).build();
				}
				//System.out.println("Pre persistencia del item");
				auxI = new ItemDeMateriaPrima(item.getCantidadEnKg(), auxLote, auxMp);
				suma = suma + auxI.getCantidadEnKg() * auxI.getMateriaPrima().getCostoPorKg();
				//itemDao.persist(auxI);
				//System.out.println("Post persistencia del item y antes de agregar a la lista y setear materia en false");
				auxLista.add(auxI);
				if (auxMp.getPeso() == 0)
					auxMp.setActivo(false);
				materiaDao.update(auxMp);
				//System.out.println("Post setear en false y antes de cerrar loop");
			}
			loteDao.persist(auxLote);
			for (ItemDeMateriaPrima i : auxLista)
				itemDao.persist(i);
			auxLote.setCostoLote(suma);
			auxLote.setMateriaPrima(auxLista);
			//System.out.println("Pre persistencia de lote");
			loteDao.update(auxLote);
			//System.out.println("Pos persistencia del lote");
			return Response.status(Response.Status.CREATED).entity(auxLote).build();
		} catch (PersistenceException e) {
			System.out.println(e.getMessage());
        	return Response.status(Response.Status.CONFLICT).entity(new JSONObject().put("message", "Hay un error con alguno de los campos").toString()).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Eliminar un lote por su ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Lote eliminado"),
	    @ApiResponse(responseCode = "404", description = "Lote no encontrado")
	})
    public Response deleteLote(@Parameter(description = "ID del lote", required = true) @PathParam("id") Integer id){
    	Lote aux = loteDao.findActiveById(id);
    	if (aux != null){
    		aux.setActivo(false);
    		loteDao.update(aux);
    		String mensaje = new JSONObject().put("message", "Lote eliminado").toString();
        	return Response.ok(mensaje).build(); 
	    } else {
	    	String mensaje = new JSONObject().put("message", "El lote no existe").toString();
        	return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build(); }
	}
	
	
}
