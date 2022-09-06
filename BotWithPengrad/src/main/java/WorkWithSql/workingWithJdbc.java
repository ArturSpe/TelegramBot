package WorkWithSql;
import java.sql.*;
import java.util.ArrayList;

public class workingWithJdbc implements dataBaseCommands {
    private String URL_DATABASE = new String();

    public workingWithJdbc (String url){

        this.URL_DATABASE = url;

    }

    Connection getConnection () throws Exception {

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
                                                                        "OR id_package = (SELECT id_package FROM phrase WHERE phrase = ?)");
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

        String toLowerSentence = sentence.toLowerCase().replaceAll("[^\\p{L}\\s]+", "");
        String [] sentenceToArray = toLowerSentence.split(" ");
        ArrayList<String> stringArrayList = new ArrayList<>(sentenceToArray.length);
        for (String word: sentenceToArray){

            stringArrayList.add("\"" + word + "\"");

        }
        String queryWords = String.join(", ", stringArrayList);

        Connection connection = getConnection();
        String query = "SELECT phrase FROM phrase NATURAL JOIN words WHERE word IN (" + queryWords + ") ORDER BY RANDOM() LIMIT 1";
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
    public String[] getPhrases (){

        String [] x = {""};
        return x;

    }

    @Override
    public void savePackage (){}

    @Override
    public void saveWord () {}

    @Override
    public void savePhrase () {}

}
