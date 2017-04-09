package im.octo.jungletree.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;

public interface HibernateService {

    EntityManager getEntityManager(String name);

    EntityManager getEntityManager(String name, Properties properties);

    EntityManagerFactory getFactory(String name);
}
