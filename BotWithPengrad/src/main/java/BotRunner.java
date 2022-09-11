import Commands.CommandProcessing;
import GettingUserData.DataFromUpdate;
import Keyboards.InlineKeyBoardTeleBot;
import Messages.MessageSender;
import UserState.UserState;
import WorkWithSql.WorkingWithJdbc;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;
import UserState.States;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotRunner extends Thread {

    private String TOKEN_BOT;
    private String DB_URL;
    private String groupForSave;
    private CountDownLatch barrier;

    private AtomicBoolean workBot = new AtomicBoolean(true);

    TelegramBot bot;
    WorkingWithJdbc withJdbc;
    UserState userState;

    public BotRunner(String token, String dbUrl, CountDownLatch barrier){

        this.TOKEN_BOT = token;
        this.DB_URL = dbUrl;
        this.bot = new TelegramBot(this.TOKEN_BOT);
        this.withJdbc = new WorkingWithJdbc("jdbc:sqlite:" + this.DB_URL);
        this.userState = new UserState();
        this.barrier = barrier;

    }

    public void stopBot(){

        workBot.set(false);

    }

    public void run () {

        GetMeResponse getMe = bot.execute(new GetMe());
        setName(getMe.user().username());
        barrier.countDown();

        this.bot.setUpdatesListener (updates -> {

            if (workBot.get()){

                updates.forEach(update -> {

                    try {

                        DataFromUpdate dataFromUpdate = new DataFromUpdate(this.bot, update);

                        if ((userState.checkUserState(dataFromUpdate.getUserId(), States.checkWord) || userState.checkUserState(dataFromUpdate.getUserId(), States.checkPhrase)) && update.callbackQuery() != null) {

                            //Хранит название выбранной группы
                            groupForSave = update.callbackQuery().data();

                        }

                        CommandProcessing.changeStateByCommand(bot, dataFromUpdate, this.userState, dataFromUpdate.getMessageText());

                        //Блок добавления слова

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkGroupsWord) && update.message() != null) {

                            MessageSender messageSender = new MessageSender("Выберите группу в которую хотите добавить слово");
                            InlineKeyBoardTeleBot inlineKeyBoardTeleBot = new InlineKeyBoardTeleBot(this.withJdbc.getPackages(), 3);
                            messageSender.Send(this.bot, dataFromUpdate.getChatId(), inlineKeyBoardTeleBot);
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.checkWord);

                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkWord) && update.callbackQuery() != null) {

                            System.out.println(dataFromUpdate.getCallbackText() + dataFromUpdate.botData().username());
                            MessageSender messageSender = new MessageSender("Вы выбрали группу: \n" + dataFromUpdate.getCallbackText() +
                                    "\nПроверьте нет ли слова, которое вы хотите добавить в базе: \n" +
                                    withJdbc.getWords(groupForSave));
                            messageSender.Send(this.bot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.saveWord);

                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.saveWord) && dataFromUpdate.getMessageText() != null) {

                            withJdbc.saveWord(dataFromUpdate.getMessageText(), groupForSave);
                            MessageSender messageSender = new MessageSender("Слово сохранено в группу:" + groupForSave + "\nВы можете добавить еще или завершить нажатием команды: /end");
                            messageSender.Send(bot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());

                        }

                        //Блок добавления фразы

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkGroupsPhrase) && update.message() != null) {

                            MessageSender messageSender = new MessageSender("Выберите группу в которую хотите добавить фразу");
                            InlineKeyBoardTeleBot inlineKeyBoardTeleBot = new InlineKeyBoardTeleBot(this.withJdbc.getPackages(), 3);
                            messageSender.Send(this.bot, dataFromUpdate.getChatId(), inlineKeyBoardTeleBot);
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.checkPhrase);

                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkPhrase) && update.callbackQuery() != null) {

                            MessageSender messageSender = new MessageSender("Вы выбрали группу: \n" + dataFromUpdate.getCallbackText() +
                                    "\nПроверьте нет ли фразы, которую вы хотите добавить в базе: \n" +
                                    withJdbc.getPhrases(groupForSave));
                            messageSender.Send(this.bot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.savePhrase);

                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.savePhrase) && dataFromUpdate.getMessageText() != null) {

                            withJdbc.savePhrase(dataFromUpdate.getMessageText(), groupForSave);
                            MessageSender messageSender = new MessageSender("Фраза сохранена в группу: " + groupForSave + "\nВы можете добавить еще или завершить нажатием команды: /end");
                            messageSender.Send(bot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());

                        }

                        //Блок основной работы

                        if (this.userState.emptyUserState(dataFromUpdate.getUserId()) && update.message() != null) {

                            MessageSender messageSender = new MessageSender(withJdbc.getPhrase(dataFromUpdate.getMessageText()));
                            messageSender.Send(bot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                });

                return UpdatesListener.CONFIRMED_UPDATES_ALL;

        }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });

    }

}
