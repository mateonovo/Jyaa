package dao;

import javax.persistence.NoResultException;
import org.jvnet.hk2.annotations.Service;
import bd.Usuario;


@Service
public class UsuarioDAO extends GenericDAOImpl<Usuario, Integer>{

	public UsuarioDAO() {
		super(Usuario.class);
	}
	
    
    public Usuario findActiveByEmail(String email) {
    	try {
    		return super.getEm().createQuery(
    	            "SELECT e FROM Usuario e WHERE e.email = :email AND e.activo = true", Usuario.class)
    	            .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } 
    }
    
    public Usuario findByEmail(String email) {
    	try {
    		return super.getEm().createQuery(
    	            "SELECT e FROM Usuario e WHERE e.email = :email", Usuario.class)
    	            .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } 
    }    
    
	public Usuario login(String email, String password) {
        try {
            return super.getEm().createQuery(
                    "SELECT e FROM Usuario e WHERE e.email = :email AND e.password = :password AND e.activo = true",
                    Usuario.class).setParameter("email", email).setParameter("password", password).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

	}
    


