package DataBase.DataForRun.Hibernate.Util;

import DataBase.DataForRun.Hibernate.Entity.BotHibernateImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtilRunner {

    private SessionFactory sessionFactory;
    private Map<String, String> settings = new HashMap<>();

    public HibernateUtilRunner (String driver, String url, String userName, String password){

        settings.put(Environment.URL, url);
        settings.put(Environment.USER, userName);
        settings.put(Environment.PASS, password);
        settings.put(Environment.DRIVER, driver);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(BotHibernateImpl.class);

        sessionFactory = metadataSources.buildMetadata().buildSessionFactory();


    }

    public SessionFactory getSessionFactory(){

        return sessionFactory;

    }

}
