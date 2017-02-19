package im.octo.jungletree.api;

import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Singleton
public final class HibernateService {

    public EntityManager getEntityManager() {
        return getDefaultFactory().createEntityManager();
    }

    public EntityManager getEntityManager(Properties properties) {
        return getDefaultFactory().createEntityManager(properties);
    }

    private EntityManagerFactory getDefaultFactory() {
        return Persistence.createEntityManagerFactory("PersistenceUnit");
    }
}
