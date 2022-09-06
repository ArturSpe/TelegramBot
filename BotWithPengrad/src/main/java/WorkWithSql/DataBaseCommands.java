package WorkWithSql;

import java.util.ArrayList;

public interface dataBaseCommands {

    String getPackage (String x);

    String[] getPackages () throws Exception;

    String[] getWords ();

    String getPhrase (String x);

    String[] getPhrases ();

    void savePackage ();

    void saveWord ();

    void savePhrase ();

}
