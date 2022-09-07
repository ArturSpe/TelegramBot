package WorkWithSql;

public class DataRunBotJdbc {

    private String tokenBot;
    private String url;

    protected DataRunBotJdbc(String tokenBot, String url){

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
