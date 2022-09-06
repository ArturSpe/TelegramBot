import WorkWithSql.WorkingRunBotWithJdbc;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {

    public static void main(String[] args) throws Exception {

        WorkingRunBotWithJdbc runBotWithJdbc = new WorkingRunBotWithJdbc("jdbc:sqlite:C:\\Users\\Artur\\Desktop\\Bot_Token_and_DB.db");

        ArrayList<String[]> strings = runBotWithJdbc.getDataForBot();
        ExecutorService executor = Executors.newFixedThreadPool(strings.size());

        for (String[] data : strings){

            BotRunner runner = new BotRunner(data[0],data[1]);
            executor.submit(runner);


        }

    }

}