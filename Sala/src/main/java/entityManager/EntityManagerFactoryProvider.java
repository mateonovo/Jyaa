package entityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
public class EntityManagerFactoryProvider implements Factory<EntityManager> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUP");

    @Override
    public EntityManager provide() {
        return emf.createEntityManager();
    }

    @Override
    public void dispose(EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
