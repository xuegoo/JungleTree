package im.octo.jungletree.db;

import com.google.inject.Singleton;
import im.octo.jungletree.api.HibernateService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Singleton
public final class JHibernateService implements HibernateService {

    // private final Map<String, EntityManagerFactory> factories = new ConcurrentHashMap<>();

    @Override
    public EntityManager getEntityManager(String name) {
        return getFactory(name).createEntityManager();
    }

    @Override
    public EntityManager getEntityManager(String name, Properties properties) {
        return getFactory(name).createEntityManager(properties);
    }

    @Override
    public EntityManagerFactory getFactory(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(name);
        // factories.put(name, factory);
        return factory;

        /*
        if (factories.containsKey(name)) {
            return factories.get(name);
        } else {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(name);
            factories.put(name, factory);
            return factory;
        }*/
    }
}
