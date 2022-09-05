package WorkWithSql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        while (resultSet.next()){

            packages.add(resultSet.getString(1));

        }

        String[] pac = packages.toArray(new String[0]);

        connection.close();

        return pac;

    }

    @Override
    public String getPackage (String x) {

        return x;

    }

    @Override
    public String[] getWords (){

        String[] x = {""};
        return x;

    }

    @Override
    public String getPhrase (String group){

        return "1";

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
