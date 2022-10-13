package DataBase.DataForRun.Hibernate;

import DataBase.DataForRun.Hibernate.Entity.BotHibernate;
import DataBase.DataForRun.Hibernate.Util.HibernateUtilRunner;
import DataBase.DataForRun.Hibernate.Entity.BotHibernateImpl;
import DataBase.DataForRun.ReturnerBots;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReturnerBotHibernate implements ReturnerBots {

    HibernateUtilRunner hibernateUtilRunner;

    public ReturnerBotHibernate(HibernateUtilRunner hibernateUtilRunner){
        this.hibernateUtilRunner = hibernateUtilRunner;
    }


    public List<? extends BotHibernate> getBot() throws Exception {

        Session session = hibernateUtilRunner.getSessionFactory().openSession();
        CriteriaQuery<BotHibernateImpl> criteriaQuery = session.getCriteriaBuilder().createQuery(BotHibernateImpl.class);
        Root<BotHibernateImpl> botRoot = criteriaQuery.from(BotHibernateImpl.class);

        criteriaQuery.select(botRoot);
        List<? extends BotHibernate> botList = new ArrayList<>();
        botList = session.createQuery(criteriaQuery).getResultList();
        session.close();

        return botList;
    }
}
