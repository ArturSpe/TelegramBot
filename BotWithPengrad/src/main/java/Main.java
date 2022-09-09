import WorkWithSql.DataForRunBotWithJdbc;
import WorkWithSql.DataRunBotJdbc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {

    public static void main(String[] args) throws Exception {

        DataForRunBotWithJdbc runBotWithJdbc = new DataForRunBotWithJdbc("jdbc:sqlite:C:\\Users\\Artur\\Desktop\\Bot_Token_and_DB.db");

        ArrayList<DataRunBotJdbc> dataSqlBots = runBotWithJdbc.getDataForBot();
        ExecutorService executor = Executors.newFixedThreadPool(dataSqlBots.size());
        CountDownLatch barrier = new CountDownLatch(dataSqlBots.size());
        ArrayList<BotRunner> botRunnerArrayList = new ArrayList<>(dataSqlBots.size());
        HashMap<String, BotRunner> botRunnerHashMap = new HashMap<>(dataSqlBots.size());

        for (DataRunBotJdbc dataSqlBot : dataSqlBots){

            BotRunner runner = new BotRunner(dataSqlBot.getTokenBot(), dataSqlBot.getUrl(), barrier);
            executor.submit(runner);
            botRunnerArrayList.add(runner);

        }

        barrier.await();

        for (BotRunner bot : botRunnerArrayList){

            botRunnerHashMap.put(bot.getName(),bot);

        }

    }

}