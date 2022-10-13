import ConfigRead.ConfigReader;
import DataBase.DataForRun.Bot;
import DataBase.DataForRun.Hibernate.Entity.BotHibernate;
import DataBase.DataForRun.Hibernate.ReturnerBotHibernate;
import DataBase.DataForRun.Hibernate.Util.HibernateUtilRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {

    public static void main(String[] args) throws Exception {

        ConfigReader configReader = new ConfigReader();

        HibernateUtilRunner dataRunBotHibernate = new HibernateUtilRunner(
                configReader.getDriver(),
                configReader.getUrlStartDb(),
                configReader.getUser(),
                configReader.getPassword());

        List<? extends Bot> dataSqlBots = new ReturnerBotHibernate(dataRunBotHibernate).getBot();
        ExecutorService executor = Executors.newFixedThreadPool(dataSqlBots.size());
        System.out.println(dataSqlBots.size());
        CountDownLatch barrier = new CountDownLatch(dataSqlBots.size());
        ArrayList<BotRunner> botRunnerArrayList = new ArrayList<>(dataSqlBots.size());
        HashMap<String, BotRunner> botRunnerHashMap = new HashMap<>(dataSqlBots.size());

        for (Bot dataSqlBot : dataSqlBots){

            BotRunner runner = new BotRunner((BotHibernate) dataSqlBot, barrier);
            executor.submit(runner);
            botRunnerArrayList.add(runner);
            System.out.println("1");

        }

        for (BotRunner bot : botRunnerArrayList){
            System.out.println("x");
            botRunnerHashMap.put(bot.getName(),bot);

        }

    }

}