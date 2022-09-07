package WorkWithSql;
import Messages.MessageToArray;

import java.sql.*;
import java.util.ArrayList;

public class WorkingWithJdbc implements IDataBaseCommands {
    private String URL_DATABASE = new String();

    public WorkingWithJdbc(String url){

        this.URL_DATABASE = url;

    }

    public Connection getConnection () throws Exception {

        return DriverManager.getConnection(this.URL_DATABASE);

    }

    public String[] getPackages () throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT package FROM packages");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        ArrayList<String>packages = new ArrayList<>();

        if (resultSet.isBeforeFirst()){
            while (resultSet.next()) {

                packages.add(resultSet.getString(1));

            }

        }

        String[] pac = packages.toArray(new String[0]);

        connection.close();
        return pac;

    }

    @Override
    public String getPackage (String wordOrPhrase) throws Exception {

        String x = wordOrPhrase.toLowerCase();
        String result = "Совпадений не обнаружено";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT package from packages" +
                                                                        "WHERE id_package = (SELECT id_package FROM words WHERE word = ?)" +
                                                                        "OR id_package = (SELECT id_package FROM phrases WHERE phrase = ?)");
        statement.setString(1, x);
        statement.setString(2, x);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        if (resultSet.isBeforeFirst()){

            result = resultSet.getString(1);

        }

        connection.close();
        return result;

    }

    @Override
    public ArrayList<String> getWords (String group) throws Exception{

        String x = group.toUpperCase();
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT word FROM words NATURAL JOIN packages WHERE package = ?");
        statement.setString(1, x);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        ArrayList<String>words = new ArrayList<>();

        if (resultSet.isBeforeFirst()){
            while (resultSet.next()) {

                words.add(resultSet.getString(1));

            }

        }

        connection.close();
        return words;

    }

    @Override
    public String getPhrase (String sentence) throws Exception{

        String queryWords = String.join(", ", MessageToArray.messageToArrayWithApostrophes(sentence));

        Connection connection = getConnection();
        String query = "SELECT phrase FROM phrases NATURAL JOIN words WHERE word IN (" + queryWords + ") ORDER BY RANDOM() LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        String phrase = "";
        if (resultSet.isBeforeFirst()){

            phrase = resultSet.getString(1);

        }

        connection.close();
        return phrase;

    }

    @Override
    public ArrayList<String> getPhrases () throws Exception{

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT phrase FROM phrases");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        ArrayList<String>arrayListPhrases = new ArrayList<>();

        if (resultSet.isBeforeFirst()){
            while (resultSet.next()) {

                arrayListPhrases.add(resultSet.getString(1));

            }

        }

        connection.close();
        return arrayListPhrases;

    }

    @Override
    public void savePackage () throws Exception{}

    @Override
    public boolean saveWord (String word, String group) throws Exception {

        Connection connection = getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO words VALUES ((SELECT id_package FROM packages WHERE package = ?), ?)");
        statement.setString(1, group);
        statement.setString(2, word);

        if (statement.executeUpdate() == 2){

            connection.commit();
            connection.close();
            return true;

        }else {

            connection.close();
            return false;

        }

    }

    @Override
    public boolean savePhrase (String phrase, String group) throws Exception {

        Connection connection = getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO phrases VALUES ((SELECT id_package FROM packages WHERE package = ?), ?)");
        statement.setString(1, group);
        statement.setString(2, phrase);

        if (statement.executeUpdate() == 2){

            connection.commit();
            connection.close();
            return true;

        }else {

            connection.close();
            return false;

        }

    }

}
