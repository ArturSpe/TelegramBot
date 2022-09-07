package WorkWithSql;

import java.util.ArrayList;

public interface IDataBaseCommands {

    String getPackage (String x) throws Exception;

    String[] getPackages () throws Exception;

    ArrayList<String> getWords (String group) throws Exception;

    String getPhrase (String x) throws Exception;

    ArrayList<String> getPhrases () throws Exception;

    void savePackage () throws Exception;

    boolean saveWord (String word, String group) throws Exception;

    boolean savePhrase (String phrase, String group) throws Exception;

}
