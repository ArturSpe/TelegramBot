

package DataBase.Methods.Hibernate;

import DataBase.DataForRun.Bot;
import Messages.MessageToArray;
import DataBase.Methods.Hibernate.Entity.Packages;
import DataBase.Methods.Hibernate.Entity.Phrase;
import DataBase.Methods.Hibernate.Entity.Words;
import DataBase.Methods.DataBaseMethods;
import DataBase.Methods.Hibernate.Utils.HibernateUtilWorking;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseMethodsHibernate implements DataBaseMethods {

    private final HibernateUtilWorking hibernateUtilWorking;

    public DataBaseMethodsHibernate(Bot botHibernate){
        hibernateUtilWorking = new HibernateUtilWorking(botHibernate);
    }

    private Session getSession (){

        return hibernateUtilWorking.getSessionFactory().openSession();

    }

    @Override
    public String getPackage(String x) throws Exception {

        try( Session session = getSession();) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packageCriteriaQuery = criteriaBuilder.createQuery(Packages.class);

            Root<Packages> packagesRoot = packageCriteriaQuery.from(Packages.class);
            Root<Phrase> phraseRoot = packageCriteriaQuery.from(Phrase.class);
            Root<Words> wordsRoot = packageCriteriaQuery.from(Words.class);

            Predicate predicateForPhrase = criteriaBuilder.equal(phraseRoot.get("phrase"), x);
            Predicate predicateForWord = criteriaBuilder.equal(wordsRoot.get("word"), x);
            Predicate predicatePhraseOrWord = criteriaBuilder.or(predicateForWord, predicateForPhrase);

            packageCriteriaQuery.where(predicateForPhrase);
            Packages packages = session.createQuery(packageCriteriaQuery).getSingleResult();

            return packages.getName();

        }catch (Exception e){

            System.out.println("?????? ? getPackage:" + e);
            return "???-?? ????? ?? ???";

        }

    }

    @Override
    public String[] getPackages() throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packagesCriteriaQuery = builder.createQuery(Packages.class);
            Root<Packages> packagesRoot = packagesCriteriaQuery.from(Packages.class);

            packagesCriteriaQuery.select(packagesRoot);
            ArrayList<String> packages = new ArrayList<>();
            for (Packages packages1 : session.createQuery(packagesCriteriaQuery).getResultList()){
                packages.add(packages1.getName());
            }
            return packages.toArray(new String[0]);
        }
    }

    @Override
    public List<String> getWords(String group) throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packagesCriteriaQuery = builder.createQuery(Packages.class);
            Root<Packages> packagesRoot = packagesCriteriaQuery.from(Packages.class);

            packagesCriteriaQuery.select(packagesRoot).where(builder.equal(packagesRoot.get("name"), group));
            List<Words> words = session.createQuery(packagesCriteriaQuery).getSingleResult().getWord();
            List<String> stringList = words.stream().map(Words::getWord).toList();

            return stringList;
        }
    }

    @Override
    public String getPhrase(String sentence) throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Words> wordsCriteriaQuery = builder.createQuery(Words.class);
            Root<Words> wordsRoot = wordsCriteriaQuery.from(Words.class);

            wordsCriteriaQuery.select(wordsRoot).where(wordsRoot.get("word").in(MessageToArray.messageToArrayWithApostrophes(sentence)));
            Words word = session.createQuery(wordsCriteriaQuery).getSingleResult();

            return word.getPackages().getPhrase().get((int) (Math.random() * word.getPackages().getPhrase().size())).getPhrase();
        }catch (Exception e){return "";}
    }

    @Override
    public List<String> getPhrases(String group) throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packagesCriteriaQuery = builder.createQuery(Packages.class);
            Root<Packages> packagesRoot = packagesCriteriaQuery.from(Packages.class);

            packagesCriteriaQuery.select(packagesRoot).where(builder.equal(packagesRoot.get("name"), group));
            List<Phrase> phrase = session.createQuery(packagesCriteriaQuery).getSingleResult().getPhrase();
            List<String> stringList = phrase.stream().map(Phrase::getPhrase).toList();

            return stringList;
        }
    }

    @Override
    public boolean saveWord(String word, String group) throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packagesCriteriaQuery = builder.createQuery(Packages.class);
            Root<Packages> packagesRoot = packagesCriteriaQuery.from(Packages.class);

            System.out.println(group);
            packagesCriteriaQuery.select(packagesRoot).where(builder.equal(packagesRoot.get("name"), group));
            long id = session.createQuery(packagesCriteriaQuery).getSingleResult().getId();

            Words words = new Words();
            words.setWord(word);
            words.setIdPackage(id);
            System.out.println(words.getWord() + words.getIdPackage());
            session.persist(words);
            session.beginTransaction();
            session.getTransaction().commit();
            return true;

        }catch (Exception e){return false;}
    }

    @Override
    public boolean savePhrase(String phrase, String group) throws Exception {
        try (Session session = hibernateUtilWorking.getSessionFactory().openSession()){

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Packages> packagesCriteriaQuery = builder.createQuery(Packages.class);
            Root<Packages> packagesRoot = packagesCriteriaQuery.from(Packages.class);

            System.out.println(group);
            packagesCriteriaQuery.select(packagesRoot).where(builder.equal(packagesRoot.get("name"), group));
            long id = session.createQuery(packagesCriteriaQuery).getSingleResult().getId();

            Phrase phrase1 = new Phrase();
            phrase1.setPhrase(phrase);
            phrase1.setIdPackage(id);
            session.persist(phrase1);
            session.beginTransaction();
            session.getTransaction().commit();

            return true;
        }catch (Exception e){return false;}
    }

    @Override
    public void savePackage() throws Exception {}

}
