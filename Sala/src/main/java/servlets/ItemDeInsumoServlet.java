package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bd.Insumo;
import bd.ItemDeInsumo;
import bd.StockProductoTerminado;
import dao.InsumoDAO;
import dao.ItemDeInsumoDAO;
import dao.StockProductoTerminadoDAO;


public class ItemDeInsumoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemDeInsumoDAO itemDeInsumoDAO = new ItemDeInsumoDAO();
		InsumoDAO insumoDAO = new InsumoDAO();
		Insumo insumo = insumoDAO.findById(1);
		Insumo insumo2 = insumoDAO.findById(2);
		StockProductoTerminadoDAO stockDAO = new StockProductoTerminadoDAO();
		StockProductoTerminado stock = stockDAO.findById(1);
		
		ItemDeInsumo item = new ItemDeInsumo(20, stock, insumo);
		ItemDeInsumo otroItem = new ItemDeInsumo(15, stock, insumo2);
		itemDeInsumoDAO.persist(item);
		itemDeInsumoDAO.persist(otroItem);
        List<ItemDeInsumo> items = itemDeInsumoDAO.findAll();
        for (ItemDeInsumo i : items) {
        	System.out.println("ITEM: " + i.getInsumo().getNombre() + " - " + i.getStock().getNombre());
        }
        
       	System.out.println("---Eliminacion de " + item.getInsumo().getNombre() + " - " + item.getStock().getNombre() + " -----");
        
        itemDeInsumoDAO.delete(item);
        items = itemDeInsumoDAO.findAll();
        for (ItemDeInsumo i : items) {
        	System.out.println("ITEM: " + i.getInsumo().getNombre() + " - " + i.getStock().getNombre());
        }
        

        System.out.println("Cantidad vieja del item " + otroItem.getCantidad());
        otroItem.setCantidad(30);
        itemDeInsumoDAO.update(otroItem);
        ItemDeInsumo encontrado = itemDeInsumoDAO.findById(2);
        System.out.println("Cantidad nueva del item " + encontrado.getCantidad());
        
        System.out.println("-----------------");

	}



}
