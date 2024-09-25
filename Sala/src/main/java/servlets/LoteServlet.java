package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import bd.Lote;
import bd.Usuario;
import dao.LoteDAO;
import dao.UsuarioDAO;


public class LoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoteDAO loteDAO = new LoteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.findById(1);
		
		Lote lote = new Lote("Jugos DETOX Naranja", "2024NARLIQ0106", LocalDate.of(2024, 5, 20), 100, 19500, usuario);

		loteDAO.persist(lote);
		
        List<Lote> lotes = loteDAO.findAll();
        for (Lote l : lotes) {
        	System.out.println(l.getNombre());
        }
        
       	System.out.println("---Eliminacion de " + lote.getNombre()+ " -----");
        
        loteDAO.delete(lote);
        lotes = loteDAO.findAll();
        for (Lote l : lotes) {
        	System.out.println(l.getNombre());
        }
        
        
        Lote lote1 = loteDAO.findById(1);
        System.out.println("Nombre viejo del lote " + lote1.getNombre());
        lote1.setNombre("Exprimido Natural Naranja");
        loteDAO.update(lote1);
        Lote encontrado = loteDAO.findById(1);
        System.out.println("Nombre nuevo del lote " + encontrado.getNombre());
        
        System.out.println("-----------------");

	}



}
