package servlets;

import jakarta.servlet.ServletException;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import bd.StockProductoTerminado;
import dao.StockProductoTerminadoDAO;


public class StockProductoTerminadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockProductoTerminadoDAO stockDAO = new StockProductoTerminadoDAO();

		StockProductoTerminado stock = new StockProductoTerminado("Frascos 360cc Mermelada Naranja", LocalDate.of(2024, 5, 30), 750, LocalDate.of(2024, 8, 30), 100);

		stockDAO.persist(stock);
		
        List<StockProductoTerminado> stocks = stockDAO.findAll();
        for (StockProductoTerminado s : stocks) {
        	System.out.println(s.getNombre());
        }
        
       	System.out.println("---Eliminacion de " + stock.getNombre()+ " -----");
        
        stockDAO.delete(stock);
        stocks = stockDAO.findAll();
        for (StockProductoTerminado s : stocks) {
        	System.out.println(s.getNombre());
        }
        
        
        StockProductoTerminado stock1 = stockDAO.findById(1);
        System.out.println("Nombre viejo del stock " + stock1.getNombre());
        stock1.setNombre("Botellas 600ml Jugo Naranja");
        stockDAO.update(stock1);
        StockProductoTerminado encontrado = stockDAO.findById(1);
        System.out.println("Nombre nuevo del stock " + encontrado.getNombre());
        
		System.out.println("----------------");


	}



}
