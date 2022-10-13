import Commands.CommandProcessing;
import DataBase.DataForRun.Hibernate.Entity.BotHibernate;
import DataBase.DataForRun.JDBC.BotJdbc;
import GettingUserData.DataFromUpdate;
import Keyboards.InlineKeyBoardTeleBot;
import Messages.MessageSender;
import UserState.UserState;
import DataBase.Methods.DataBaseMethods;
import DataBase.Methods.Hibernate.DataBaseMethodsHibernate;
import DataBase.Methods.JDBC.DataBaseMethodsJdbc;
import DataBase.DataForRun.Bot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;
import UserState.States;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotRunner extends Thread {

    private String groupForSave;
    private final AtomicBoolean workBot = new AtomicBoolean(true);
    TelegramBot telegramBot;
    DataBaseMethods dataBase;
    UserState userState;



    public BotRunner(BotJdbc bot, CountDownLatch barrier){

        String TOKEN_BOT = bot.getTokenBot();
        String DB_URL = bot.getUrl();
        telegramBot = new TelegramBot(TOKEN_BOT);
        dataBase = new DataBaseMethodsJdbc("jdbc:sqlite:" + DB_URL);
        this.userState = new UserState();

    }

    public BotRunner(BotHibernate botData, CountDownLatch barrier){

        telegramBot = new TelegramBot(botData.getTokenBot());
        dataBase = new DataBaseMethodsHibernate(botData);
        userState = new UserState();
        barrier = barrier;

    }


    public void stopBot(){

        workBot.set(false);

    }

    public void run () {

        GetMeResponse getMe = telegramBot.execute(new GetMe());
        setName(getMe.user().username());
        telegramBot.setUpdatesListener (updates -> {

            if (workBot.get()){
                updates.forEach(update -> {

                    try {

                        DataFromUpdate dataFromUpdate = new DataFromUpdate(telegramBot, update);

                        if ((userState.checkUserState(dataFromUpdate.getUserId(), States.checkWord) || userState.checkUserState(dataFromUpdate.getUserId(), States.checkPhrase)) && update.callbackQuery() != null) {
                            //Хранит название выбранной группы
                            groupForSave = update.callbackQuery().data();
                        }
                        //Обработка первичный команд
                        CommandProcessing.changeStateByCommand(telegramBot, dataFromUpdate, this.userState, dataFromUpdate.getMessageText());

                        //Блок добавления слова
                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkGroupsWord) && update.message() != null) {
                            MessageSender messageSender = new MessageSender("Выберите группу в которую хотите добавить слово");
                            InlineKeyBoardTeleBot inlineKeyBoardTeleBot = new InlineKeyBoardTeleBot(this.dataBase.getPackages(), 3);
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), inlineKeyBoardTeleBot);
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.checkWord);
                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkWord) && update.callbackQuery() != null) {
                            System.out.println(dataFromUpdate.getCallbackText() + dataFromUpdate.botData().username());
                            MessageSender messageSender = new MessageSender("Вы выбрали группу: \n" + dataFromUpdate.getCallbackText() +
                                    "\nПроверьте нет ли слова, которое вы хотите добавить в базе: \n" +
                                    dataBase.getWords(groupForSave));
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.saveWord);
                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.saveWord) && dataFromUpdate.getMessageText() != null) {
                            dataBase.saveWord(dataFromUpdate.getMessageText(), groupForSave);
                            MessageSender messageSender = new MessageSender("Слово сохранено в группу:" + groupForSave + "\nВы можете добавить еще или завершить нажатием команды: /end");
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                        }

                        //Блок добавления фразы
                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkGroupsPhrase) && update.message() != null) {
                            MessageSender messageSender = new MessageSender("Выберите группу в которую хотите добавить фразу");
                            InlineKeyBoardTeleBot inlineKeyBoardTeleBot = new InlineKeyBoardTeleBot(this.dataBase.getPackages(), 3);
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), inlineKeyBoardTeleBot);
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.checkPhrase);
                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.checkPhrase) && update.callbackQuery() != null) {
                            MessageSender messageSender = new MessageSender("Вы выбрали группу: \n" + dataFromUpdate.getCallbackText() +
                                    "\nПроверьте нет ли фразы, которую вы хотите добавить в базе: \n" +
                                    dataBase.getPhrases(groupForSave));
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                            this.userState.setUserState(dataFromUpdate.getUserId(), States.savePhrase);
                        }

                        if (this.userState.checkUserState(dataFromUpdate.getUserId(), States.savePhrase) && dataFromUpdate.getMessageText() != null) {
                            dataBase.savePhrase(dataFromUpdate.getMessageText(), groupForSave);
                            MessageSender messageSender = new MessageSender("Фраза сохранена в группу: " + groupForSave + "\nВы можете добавить еще или завершить нажатием команды: /end");
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
                        }

                        //Блок основной работы
                        if (this.userState.emptyUserState(dataFromUpdate.getUserId()) && update.message() != null) {
                            MessageSender messageSender = new MessageSender(dataBase.getPhrase(dataFromUpdate.getMessageText()));
                            messageSender.Send(telegramBot, dataFromUpdate.getChatId(), dataFromUpdate.getMessageId());
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