package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import jakarta.inject.Inject;


public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

	private Class<T> entityClass;

	@Inject
	private EntityManager em;
	

	public Class<T> getEntityClass() {
		return entityClass;
	}


	public EntityManager getEm() {
		return em;
	}


	public GenericDAOImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	
    @Override
    public void persist(T entity) {
    	try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }


    @Override
    public T findById(ID id) {
    	return em.find(entityClass, id);
    }
    
    @Override
    public T findActiveById(ID id) {
    	List<T> result = em.createQuery(
    			"SELECT e FROM " + entityClass.getName() + " e WHERE e.id = :id AND e.activo = true", entityClass)
    	         .setParameter("id", id).getResultList();
    	         return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<T> findAllActives() {
    		List<T> result = em.createQuery(
    	            "SELECT e FROM " + entityClass.getName() + " e WHERE e.activo = true", entityClass)
    	            .getResultList();
    	    return result.isEmpty() ? null : result;
    }
    
    @Override
    public List<T> findAll() {
        List<T> result = em.createQuery(
                "SELECT e FROM " + entityClass.getName() + " e", entityClass)
                .getResultList();
        return result.isEmpty() ? null : result;
    }



    @Override
    public void update(T entity) {
    	try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } 
    }

    @Override
    public void delete(T entity) {
    	try {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } 
    }
    
    @Override
    public T getByName(String name) {
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getName() + " e WHERE e.nombre = :name", entityClass)
                     .setParameter("name", name)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la consulta", e);
        } 
    }
}