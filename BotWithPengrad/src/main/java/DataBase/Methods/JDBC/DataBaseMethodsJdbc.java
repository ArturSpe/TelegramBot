package WorkWithSql.Methods.JDBC;
import Messages.MessageToArray;
import WorkWithSql.Methods.DataBaseMethods;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseMethodsJDBC implements DataBaseMethods {
    private String URL_DATABASE = new String();

    public DataBaseMethodsJDBC(String url){

        this.URL_DATABASE = url;

    }

    public Connection getConnection () throws Exception {

        return DriverManager.getConnection(this.URL_DATABASE);

    }

    public String[] getPackages () throws Exception {

        try(Connection connection = getConnection()) {


            PreparedStatement statement = connection.prepareStatement("SELECT package FROM packages");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            ArrayList<String> packages = new ArrayList<>();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    packages.add(resultSet.getString(1));

                }

            }

            String[] pac = packages.toArray(new String[0]);

            connection.close();
            return pac;

        }catch (Exception e){

            String [] x = {"Что то пошло не так"};
            return x;

        }

    }

    @Override
    public String getPackage (String wordOrPhrase) throws Exception {

        String x = wordOrPhrase.toLowerCase();
        String result = "Совпадений не обнаружено";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT package from packages" +
                                                                            "WHERE id_package = (SELECT id_package FROM words WHERE word = ?)" +
                                                                                "OR id_package = (SELECT id_package FROM phrases WHERE phrase = ?)");
            statement.setString(1, x);
            statement.setString(2, x);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.isBeforeFirst()) {

                result = resultSet.getString(1);

            }

            connection.close();
            return result;

        }catch (Exception e){

            return "Что то пошло не так";

        }

    }

    @Override
    public ArrayList<String> getWords (String group) throws Exception{

        try (Connection connection = getConnection()) {

            String x = group.toUpperCase();
            PreparedStatement statement = connection.prepareStatement("SELECT word FROM words NATURAL JOIN packages WHERE package = ?");
            statement.setString(1, x);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            ArrayList<String> words = new ArrayList<>();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    words.add(resultSet.getString(1));

                }

            }

            return words;
        }

    }

    @Override
    public String getPhrase (String sentence) throws Exception{


        try (Connection connection = getConnection()) {

            String queryWords = String.join(", ", MessageToArray.messageToArrayWithApostrophes(sentence));
            String query = "SELECT phrase FROM phrases NATURAL JOIN words WHERE word IN (" + queryWords + ") ORDER BY RANDOM() LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            String phrase = "";
            if (resultSet.isBeforeFirst()) {

                phrase = resultSet.getString(1);

            }

            connection.close();
            return phrase;
        }

    }

    @Override
    public ArrayList<String> getPhrases (String group) throws Exception{

        try (Connection connection = getConnection()) {

            String x = group.toUpperCase();
            PreparedStatement statement = connection.prepareStatement("SELECT phrase FROM phrases NATURAL JOIN packages WHERE package = ?");
            statement.setString(1, x);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            ArrayList<String> arrayListPhrases = new ArrayList<>();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    arrayListPhrases.add(resultSet.getString(1));

                }

            }

            return arrayListPhrases;
        }

    }

    @Override
    public void savePackage () throws Exception{}

    @Override
    public boolean saveWord (String word, String group) throws Exception {

        try (Connection connection = getConnection()) {

            word = word.toLowerCase();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO words (id_package, word) VALUES ((SELECT id_package FROM packages WHERE package = ?), ?)");
            statement.setString(1, group);
            statement.setString(2, word);

            if (statement.executeUpdate() == 1) {

                connection.commit();
                return true;

            } else {

                return false;

            }

        }catch (Exception e){

            return false;

        }

    }

    @Override
    public boolean savePhrase (String phrase, String group) throws Exception {

        try (Connection connection = getConnection()) {


            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO phrases (id_package, phrase) VALUES ((SELECT id_package FROM packages WHERE package = ?), ?)");
            statement.setString(1, group);
            statement.setString(2, phrase);

            if (statement.executeUpdate() == 1) {

                connection.commit();
                return true;

            } else {

                return false;

            }

        }catch (Exception e){

            e.printStackTrace();
            return false;

        }

    }

}
