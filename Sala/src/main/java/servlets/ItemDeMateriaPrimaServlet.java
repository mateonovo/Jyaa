package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bd.ItemDeMateriaPrima;
import bd.Lote;
import bd.MateriaPrima;
import dao.ItemDeMateriaPrimaDAO;
import dao.LoteDAO;
import dao.MateriaPrimaDAO;


public class ItemDeMateriaPrimaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemDeMateriaPrimaDAO itemDeMateriaPrimaDAO = new ItemDeMateriaPrimaDAO();
		MateriaPrimaDAO materiaDAO = new MateriaPrimaDAO();
		MateriaPrima mp1 = materiaDAO.findById(1);
		MateriaPrima mp2 = materiaDAO.findById(2);
		LoteDAO loteDAO = new LoteDAO();
		Lote lote = loteDAO.findById(1);
		
		ItemDeMateriaPrima item = new ItemDeMateriaPrima(10, lote, mp1);
		ItemDeMateriaPrima otroItem = new ItemDeMateriaPrima(20, lote, mp2);
		itemDeMateriaPrimaDAO.persist(item);
		itemDeMateriaPrimaDAO.persist(otroItem);
        List<ItemDeMateriaPrima> items = itemDeMateriaPrimaDAO.findAll();
        for (ItemDeMateriaPrima i : items) {
        	System.out.println("ITEM: " + i.getLote().getNombre() + " - " + i.getMateriaPrima().getNombre());
        }
        
       	System.out.println("---Eliminacion de " + item.getLote().getNombre() + " - " + item.getMateriaPrima().getNombre() + " -----");
        
        itemDeMateriaPrimaDAO.delete(item);
        items = itemDeMateriaPrimaDAO.findAll();
        for (ItemDeMateriaPrima i : items) {
        	System.out.println("ITEM: " + i.getLote().getNombre() + " - " + i.getMateriaPrima().getNombre());
        }
        

        System.out.println("Cantidad vieja del item " + otroItem.getCantidadEnKg());
        otroItem.setCantidadEnKg(5);
        itemDeMateriaPrimaDAO.update(otroItem);
        ItemDeMateriaPrima encontrado = itemDeMateriaPrimaDAO.findById(4);
        System.out.println("Cantidad nueva del item " + encontrado.getCantidadEnKg());
        
        System.out.println("-----------------");

	}



}
