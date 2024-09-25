package api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bd.CanalDeVenta;
import bd.FamiliaProductora;
import bd.Insumo;
import bd.ItemDeMateriaPrima;
import bd.Lote;
import bd.MateriaPrima;
import bd.StockProductoTerminado;
import bd.Usuario;
import dao.CanalDeVentaDAO;
import dao.FamiliaProductoraDAO;
import dao.InsumoDAO;
import dao.ItemDeMateriaPrimaDAO;
import dao.LoteDAO;
import dao.MateriaPrimaDAO;
import dao.StockProductoTerminadoDAO;
import dao.UsuarioDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/cargarBD")
public class Api {
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private FamiliaProductoraDAO familiaDAO;
	
	@Inject
	private MateriaPrimaDAO materiaDAO;
	
	@Inject
	private LoteDAO loteDAO;
	
	@Inject
	private ItemDeMateriaPrimaDAO itemDAO;
	

	@Inject
	private CanalDeVentaDAO canalDAO;
	
	@Inject
	private InsumoDAO insumoDAO;

	
	@GET
	public Response cargar() {
    	Usuario user = new Usuario("jose@gmail.com", "Jose", "Perez", "1234");
    	usuarioDAO.persist(user);
    	
    	LocalDate date = LocalDate.of(2020, 1, 8);
    	FamiliaProductora familia = new FamiliaProductora("flia", date, "centro");
    	familiaDAO.persist(familia);

    	LocalDate compra = LocalDate.of(2024, 5, 25);
    	LocalDate vencimiento = LocalDate.of(2024, 6, 25);
    	MateriaPrima materia = new MateriaPrima("Naranjas", 50, compra, vencimiento, 1000, "heladera", familia);
    	MateriaPrima materia2 = new MateriaPrima("Azucar", 10, compra, vencimiento, 750, "alancena", familia);
    	MateriaPrima materia3 = new MateriaPrima("Manzanas", 40, compra, vencimiento, 1300, "heladera", familia);
    	MateriaPrima materia4 = new MateriaPrima("Peras", 30, compra, vencimiento, 600, "heladera", familia);
    	materiaDAO.persist(materia);
    	materiaDAO.persist(materia2); 
    	materiaDAO.persist(materia3); 
    	materiaDAO.persist(materia4); 
    	
    	Lote lote = new Lote("Mermelada de naranja", "A001", compra, 20, 20000, user);
    	Lote lote2 = new Lote("Mermelada de manzana", "A002", compra, 15, 15000, user);
    	Lote lote3 = new Lote("Mermelada de pera", "A003", compra, 10, 10000, user);
    	loteDAO.persist(lote);
    	loteDAO.persist(lote2);
    	loteDAO.persist(lote3);
    	
    	ItemDeMateriaPrima item = new ItemDeMateriaPrima(25, lote, materia);
    	ItemDeMateriaPrima item2 = new ItemDeMateriaPrima(2, lote, materia2);
    	ItemDeMateriaPrima item3 = new ItemDeMateriaPrima(2, lote2, materia2);
    	ItemDeMateriaPrima item4 = new ItemDeMateriaPrima(25, lote2, materia3);	
    	ItemDeMateriaPrima item5 = new ItemDeMateriaPrima(2, lote3, materia2);
    	ItemDeMateriaPrima item6 = new ItemDeMateriaPrima(25, lote3, materia4);
    	itemDAO.persist(item);
    	itemDAO.persist(item2);
    	itemDAO.persist(item3);
    	itemDAO.persist(item4);
    	itemDAO.persist(item5);
    	itemDAO.persist(item6);
    	
    	List<ItemDeMateriaPrima> lista = new ArrayList<>();
    	lista.add(item);
    	lista.add(item2);
    	
    	lote.setMateriaPrima(lista);
    	loteDAO.update(lote);
    	
    	List<ItemDeMateriaPrima> lista2 = new ArrayList<>();
    	lista2.add(item3);
    	lista2.add(item4);
    	
    	lote2.setMateriaPrima(lista2);
    	loteDAO.update(lote2);
    	
    	List<ItemDeMateriaPrima> lista3 = new ArrayList<>();
    	lista3.add(item5);
    	lista3.add(item6);
    	
    	lote3.setMateriaPrima(lista3);
    	loteDAO.update(lote3);
    
    	CanalDeVenta canal = new CanalDeVenta("canal predeterminado", "La Plata");
    	canalDAO.persist(canal);

    	Insumo insumo = new Insumo("Frascos 360cc", 200, 45.0);
    	Insumo otroInsumo = new Insumo("Tapitas", 1000, 50);
    	insumoDAO.persist(insumo);
    	insumoDAO.persist(otroInsumo);

    	return Response.ok().build();
	}

}
