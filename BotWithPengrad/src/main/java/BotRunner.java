import Commands.CommandProcessing;
import GettingUserData.UpdateData;
import Keyboards.InlineKeyBoardTeleBot;
import Messages.MessageSender;
import UserState.UserState;
import WorkWithSql.WorkingWithJdbc;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

public class BotRunner implements Runnable{

    private String TOKEN_BOT;
    private String DB_URL;

    public BotRunner(String token, String dbUrl){

        this.TOKEN_BOT = token;
        this.DB_URL = dbUrl;

    }

    public void run () {

        TelegramBot bot = new TelegramBot(this.TOKEN_BOT);
        WorkingWithJdbc withJdbc = new WorkingWithJdbc("jdbc:sqlite:" + this.DB_URL);
        UserState userState = new UserState();

        bot.setUpdatesListener(updates -> {

            updates.forEach(update -> {

                UpdateData userMetaData = new UpdateData(update);

                long chatId = userMetaData.getChatId();
                long userId = userMetaData.getUserId();
                int messageId = userMetaData.getMessageId();

                try {

                    if (update.message() != null) {

                        CommandProcessing.changeStateByCommand(bot, chatId, userId, messageId, userState, update.message().text());

                        if (update.message().text().equals("1")) {

                            if (userState.emptyUserState(userId)) {
                                MessageSender messageSender = new MessageSender("Пустое состояние");
                                messageSender.Send(bot, chatId);

                            } else {

                                MessageSender messageSender = new MessageSender(userState.checkState(userId).toString());
                                messageSender.Send(bot, chatId);

                            }

                        }

                        if (update.message().text().equals("2") && userState.emptyUserState(userId)) {

                            String[] key = withJdbc.getPackages();
                            MessageSender messageSender = new MessageSender("Клавиатура");
                            Keyboards.InlineKeyBoardTeleBot keyBoardTeleBot = new InlineKeyBoardTeleBot(key, 3);
                            messageSender.Send(bot, chatId, keyBoardTeleBot);

                        }

                        if (userState.emptyUserState(userId) && update.message().text() != null) {

                            String q = update.message().text();
                            MessageSender messageSender = new MessageSender(withJdbc.getPhrase(userMetaData.getMessageText()));
                            messageSender.Send(bot, chatId, messageId);

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
