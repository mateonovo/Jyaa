package config;
import javax.persistence.EntityManager;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import dao.CanalDeVentaDAO;
import dao.FamiliaProductoraDAO;
import dao.InsumoDAO;
import dao.ItemDeInsumoDAO;
import dao.ItemDeMateriaPrimaDAO;
import dao.LoteDAO;
import dao.MateriaPrimaDAO;
import dao.PermisoDAO;
import dao.RecetaDAO;
import dao.StockProductoTerminadoDAO;
import dao.UsuarioDAO;
import entityManager.EntityManagerFactoryProvider;


public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
	//	bind(UsuarioDAO.class).to(IUsuarioDAO.class).in(RequestScoped.class);
	//	bind(UsuarioDAO.class).to(new TypeLiteral<GenericDAO<Usuario, Integer>>() {}).in(RequestScoped.class);
        bindFactory(EntityManagerFactoryProvider.class).to(EntityManager.class).in(RequestScoped.class);
		bind(UsuarioDAO.class).to(UsuarioDAO.class).in(RequestScoped.class);
		bind(CanalDeVentaDAO.class).to(CanalDeVentaDAO.class).in(RequestScoped.class);
		bind(FamiliaProductoraDAO.class).to(FamiliaProductoraDAO.class).in(RequestScoped.class);
		bind(InsumoDAO.class).to(InsumoDAO.class).in(RequestScoped.class);
		bind(ItemDeInsumoDAO.class).to(ItemDeInsumoDAO.class).in(RequestScoped.class);
		bind(ItemDeMateriaPrimaDAO.class).to(ItemDeMateriaPrimaDAO.class).in(RequestScoped.class);
		bind(LoteDAO.class).to(LoteDAO.class).in(RequestScoped.class);
		bind(MateriaPrimaDAO.class).to(MateriaPrimaDAO.class).in(RequestScoped.class);
		bind(PermisoDAO.class).to(PermisoDAO.class).in(RequestScoped.class);
		bind(RecetaDAO.class).to(RecetaDAO.class).in(RequestScoped.class);
		bind(StockProductoTerminadoDAO.class).to(StockProductoTerminadoDAO.class).in(RequestScoped.class);

		
	}

}
