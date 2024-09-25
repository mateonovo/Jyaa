package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bd.Insumo;
import dao.InsumoDAO;


public class InsumoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InsumoDAO insumoDAO = new InsumoDAO();
		Insumo insumo = new Insumo("Frascos 500cc", 100, 100);
		Insumo otroInsumo = new Insumo("Botellas", 1000, 50);
		insumoDAO.persist(insumo);
		insumoDAO.persist(otroInsumo);
        List<Insumo> insumos = insumoDAO.findAll();
        System.out.println("Insumos  en BD");
        for (Insumo i : insumos) {
        	System.out.println(i.getNombre());
        }
        
       	System.out.println("---Eliminacion-----");
        
       	
        insumoDAO.delete(otroInsumo);
        insumos = insumoDAO.findAll();
        System.out.println("Insumos  en BD");
        for (Insumo i : insumos) {
        	System.out.println(i.getNombre());
        }
        

        System.out.println("Cantidad vieja de frascos 500cc: " + insumo.getCantidad());
        insumo.setCantidad(50);
        insumoDAO.update(insumo);
        Insumo encontrado = insumoDAO.findById(3);
        System.out.println("Cantidad nueva de frascos 500cc: " + encontrado.getCantidad());
        
        System.out.println("-----------------");

	}



}
