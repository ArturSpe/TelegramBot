package WorkWithSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class WorkingRunBotWithJdbc implements DataBaseRunBot {

    private String dbWithBotsUrl;


    public WorkingRunBotWithJdbc (String dbWithBotsUrl){

        this.dbWithBotsUrl = dbWithBotsUrl;

    }
    @Override
    public ArrayList < String[] > getDataForBot() throws Exception{

        WorkingWithJdbc withJdbc = new WorkingWithJdbc(dbWithBotsUrl);
        Connection connection = withJdbc.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BotTokenAndDB");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        ArrayList<String[]> listArrayList = new ArrayList<>();

        while (resultSet.next()){

            String[] strings = {resultSet.getString(2), resultSet.getString(3)};
            listArrayList.add(strings);

        }

        return listArrayList;

    }

}
