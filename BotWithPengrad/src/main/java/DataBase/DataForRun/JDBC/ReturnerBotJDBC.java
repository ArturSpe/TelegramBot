package DataBase.DataForRunBot.JDBC;

import DataBase.DataForRunBot.ReturnerBots;
import DataBase.Methods.JDBC.DataBaseMethodsJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReturnerBotJDBC implements ReturnerBots {

    private String dbWithBotsUrl;


    public ReturnerBotJDBC(String dbWithBotsUrl){

        this.dbWithBotsUrl = dbWithBotsUrl;

    }
    @Override
    public List<? extends BotJdbc> getBot() throws Exception{

        DataBaseMethodsJdbc withJdbc = new DataBaseMethodsJdbc(dbWithBotsUrl);
        Connection connection = withJdbc.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BotTokenAndDB");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        ArrayList<BotJdbc> listArrayList = new ArrayList<>();

        while (resultSet.next()){

            BotJdbcImpl dataSqlBot = new BotJdbcImpl(resultSet.getString(2), resultSet.getString(3));
            listArrayList.add(dataSqlBot);

        }

        return listArrayList;

    }

}
