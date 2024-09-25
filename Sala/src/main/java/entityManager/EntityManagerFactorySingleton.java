package entityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

    
    private static EntityManagerFactory emf;
    
    
    private EntityManagerFactorySingleton() {}

    
    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            synchronized (EntityManagerFactorySingleton.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("miUP");
                }
            }
        }
        return emf;
    }

    
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
