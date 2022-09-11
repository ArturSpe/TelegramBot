package WorkWithSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataForRunBotWithJdbc implements IDataBaseRunBot {

    private String dbWithBotsUrl;


    public DataForRunBotWithJdbc(String dbWithBotsUrl){

        this.dbWithBotsUrl = dbWithBotsUrl;

    }
    @Override
    public ArrayList <DataRunBotJdbc> getDataForBot() throws Exception{

        WorkingWithJdbc withJdbc = new WorkingWithJdbc(dbWithBotsUrl);
        Connection connection = withJdbc.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BotTokenAndDB");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        ArrayList<DataRunBotJdbc> listArrayList = new ArrayList<>();

        while (resultSet.next()){

            DataRunBotJdbc dataSqlBot = new DataRunBotJdbc(resultSet.getString(2), resultSet.getString(3));
            listArrayList.add(dataSqlBot);

        }

        return listArrayList;

    }

}
