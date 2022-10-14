package DataBase.Methods.Hibernate.Utils;

import ConfigRead.ConfigReader;
import DataBase.DataForRun.Bot;
import DataBase.Methods.Hibernate.Entity.Packages;
import DataBase.Methods.Hibernate.Entity.Phrase;
import DataBase.Methods.Hibernate.Entity.Words;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtilWorking {

    private SessionFactory sessionFactory;
    private Map<String, String> settings = new HashMap<>();

    public HibernateUtilWorking (Bot botHibernate){

        ConfigReader configReader = new ConfigReader();
        settings.put(Environment.URL, botHibernate.getUrl());
        settings.put(Environment.DRIVER, configReader.getDriver());
        settings.put(Environment.USER, configReader.getUser());
        settings.put(Environment.PASS, configReader.getPassword());

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Words.class)
                .addAnnotatedClass(Packages.class)
                .addAnnotatedClass(Phrase.class);

        try {

            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        }catch (Exception e){

            System.out.println("Ошибка:" + e);

        }


    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
