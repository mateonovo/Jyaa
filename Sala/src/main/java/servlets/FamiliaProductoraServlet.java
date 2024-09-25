package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bd.FamiliaProductora;
import dao.FamiliaProductoraDAO;


public class FamiliaProductoraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	FamiliaProductoraDAO fDAO = new FamiliaProductoraDAO();
    	LocalDate inicio = LocalDate.of(2024, 02, 15);
    	FamiliaProductora familiaNueva = new FamiliaProductora("puerto de palos", inicio, "Ensenada");
		fDAO.persist(familiaNueva);

        List<FamiliaProductora> familias = fDAO.findAll();
        System.out.println("Familias Productoras en BD");
        for (FamiliaProductora fp : familias) {
        	System.out.println(fp.getNombre());
        }
        System.out.println();
       	System.out.println("---Eliminacion de " + familiaNueva.getNombre() + " -----");
       	
        fDAO.delete(familiaNueva);
        familias = fDAO.findAll();
        System.out.println("Familias Productoras en BD Post eliminacion");
        for (FamiliaProductora fp : familias) {
        	System.out.println(fp.getNombre());
        }
        

        FamiliaProductora familia1 = fDAO.findById(1);
        System.out.println("---Actualizacion de " + familia1.getNombre() + " -----");
        System.out.println("Zona vieja de la familia " + familia1.getZona());
        familia1.setZona("Berisso");
        fDAO.update(familia1);
        FamiliaProductora encontrado = fDAO.findById(1);
        System.out.println("Zona nueva de la familia " + encontrado.getZona());
        
        System.out.println("-----------------");
        
        System.out.println("Busqueda por nombre de familia"+ fDAO.getByName("flia"));
 

	}



}
