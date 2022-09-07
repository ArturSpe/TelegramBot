import Commands.CommandProcessing;
import GettingUserData.DataFromUpdate;
import Keyboards.InlineKeyBoardTeleBot;
import Messages.MessageSender;
import UserState.UserState;
import WorkWithSql.WorkingWithJdbc;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

public class BotRunner implements Runnable{

    private String TOKEN_BOT;
    private String DB_URL;

    TelegramBot bot;
    WorkingWithJdbc withJdbc;
    UserState userState;

    public BotRunner(String token, String dbUrl){

        this.TOKEN_BOT = token;
        this.DB_URL = dbUrl;
        this.bot = new TelegramBot(this.TOKEN_BOT);
        this.withJdbc = new WorkingWithJdbc("jdbc:sqlite:" + this.DB_URL);
        this.userState = new UserState();

    }

    public void run () {

        this.bot.setUpdatesListener(updates -> {

            updates.forEach(update -> {

                DataFromUpdate dataFromUpdate = new DataFromUpdate(this.bot, update);
                long chatId = dataFromUpdate.getChatId();
                long userId = dataFromUpdate.getUserId();
                int messageId = dataFromUpdate.getMessageId();

                try {

                    if (update.message() != null) {

                        CommandProcessing.changeStateByCommand(this.bot, dataFromUpdate, this.userState, update.message().text());

                        if (dataFromUpdate.getMessageText().equals("1")) {

                            if (userState.emptyUserState(userId)) {
                                MessageSender messageSender = new MessageSender("Пустое состояние");
                                messageSender.Send(this.bot, chatId);

                            } else {

                                MessageSender messageSender = new MessageSender(this.userState.checkState(userId).toString());
                                messageSender.Send(this.bot, chatId);

                            }

                        }

                        if (dataFromUpdate.getMessageText().equals("2") && this.userState.emptyUserState(userId)) {

                            String[] key = this.withJdbc.getPackages();
                            MessageSender messageSender = new MessageSender("Клавиатура");
                            Keyboards.InlineKeyBoardTeleBot keyBoardTeleBot = new InlineKeyBoardTeleBot(key, 3);
                            messageSender.Send(this.bot, chatId, keyBoardTeleBot);

                        }

                        if (this.userState.emptyUserState(userId) && dataFromUpdate.getMessageText() != null) {

                            MessageSender messageSender = new MessageSender(this.withJdbc.getPhrase(dataFromUpdate.getMessageText()));
                            messageSender.Send(this.bot, chatId, messageId);

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    System.exit(0);

                }

            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });

    }

}
