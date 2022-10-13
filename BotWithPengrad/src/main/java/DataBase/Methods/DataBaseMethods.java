package WorkWithSql.Methods;

import java.util.ArrayList;

public interface DataBaseMethods {

    String getPackage (String x) throws Exception;

    String[] getPackages () throws Exception;

    ArrayList<String> getWords (String group) throws Exception;

    String getPhrase (String x) throws Exception;

    ArrayList<String> getPhrases (String group) throws Exception;

    void savePackage () throws Exception;

    boolean saveWord (String word, String group) throws Exception;

    boolean savePhrase (String phrase, String group) throws Exception;

}
