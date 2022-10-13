package DataBase.DataForRunBot.JDBC;

public class BotJdbcImpl implements BotJdbc {

    private String tokenBot;
    private String url;

    protected BotJdbcImpl(String tokenBot, String url){

        this.tokenBot = tokenBot;
        this.url = url;

    }

    public String getTokenBot () {

        return this.tokenBot;

    }

    public String getUrl () {

        return this.url;

    }

}
