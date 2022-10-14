package DataBase.Methods;

import DataBase.Methods.Hibernate.Entity.Words;

import java.util.List;

public interface DataBaseMethods {

    String getPackage (String x) throws Exception;

    String[] getPackages () throws Exception;

    List<String> getWords (String group) throws Exception;

    String getPhrase (String x) throws Exception;

    List<String> getPhrases (String group) throws Exception;

    void savePackage () throws Exception;

    boolean saveWord (String word, String group) throws Exception;

    boolean savePhrase (String phrase, String group) throws Exception;

}
