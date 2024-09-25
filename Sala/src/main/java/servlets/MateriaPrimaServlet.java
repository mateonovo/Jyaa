package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bd.FamiliaProductora;
import bd.MateriaPrima;
import dao.FamiliaProductoraDAO;
import dao.MateriaPrimaDAO;


public class MateriaPrimaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
		LocalDate compra = LocalDate.of(2024, 3, 13);
    	LocalDate vencimiento = LocalDate.of(2024, 9, 13);
    	FamiliaProductoraDAO fDAO = new FamiliaProductoraDAO();
    	FamiliaProductora familia = fDAO.findById(1);
    	MateriaPrima materia = new MateriaPrima("Uvas", 50, compra, vencimiento, 1000, "heladera", familia);
		materiaPrimaDAO.persist(materia);

        List<MateriaPrima> materiasPrimas = materiaPrimaDAO.findAll();
        System.out.println("Materias Primas en BD");
        for (MateriaPrima mp : materiasPrimas) {
        	System.out.println(mp.getNombre());
        }
        System.out.println();
       	System.out.println("---Eliminacion de " + materia.getNombre() + " -----");
       	
        materiaPrimaDAO.delete(materia);
        materiasPrimas = materiaPrimaDAO.findAll();
        System.out.println("Materias Primas en BD Post eliminacion");
        for (MateriaPrima mp : materiasPrimas) {
        	System.out.println(mp.getNombre());
        }
        

        MateriaPrima materia1 = materiaPrimaDAO.findById(1);
        System.out.println("---Actualizacion de " + materia1.getNombre() + " -----");
        System.out.println("Peso viejo en kilos " + materia1.getPeso());
        materia1.setPeso(20);
        materiaPrimaDAO.update(materia1);
        MateriaPrima encontrado = materiaPrimaDAO.findById(1);
        System.out.println("Peso nuevo en kilos " + encontrado.getPeso());
        
        System.out.println("-----------------");

	}
	
	
	



}
