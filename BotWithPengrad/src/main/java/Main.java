import Commands.CommandProcessing;
import GettingUserData.*;
import Keyboards.InlineKeyBoardTeleBot;
import Messages.MessageSender;
import UserState.UserState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

import java.io.IOException;

public class Main {

    static TelegramBot bot = new TelegramBot("***");

    public static void main(String[] args) {

        UserState userState = new UserState();

        bot.setUpdatesListener(updates -> {

            updates.forEach(update -> {


                long chatId = GetChatId.getChatId(update);
                long userId = GetUserId.getUserId(update);
                int messageId = GetMessageId.getMessageId(update);

                try {

                    if (update.message() != null) {

                        CommandProcessing.changeStateByCommand(bot, chatId, userId, messageId, userState, update.message().text());

                        if (update.message().text().equals("1")) {

                            if (userState.emptyUserState(userId) == true) {
                                MessageSender messageSender = new MessageSender("Пустое состояние");
                                messageSender.Send(bot, chatId);

                            }else {

                                MessageSender messageSender = new MessageSender(userState.checkState(userId).toString());
                                messageSender.Send(bot, chatId);

                            }

                        }

                        if (update.message().text().equals("2") && userState.emptyUserState(userId) == true) {

                            String[] key = {"Кнопка 1", "Кнопка 2", "Кнопка 3"};
                            MessageSender messageSender = new MessageSender("Клавиатура");
                            Keyboards.InlineKeyBoardTeleBot keyBoardTeleBot = new InlineKeyBoardTeleBot(key, 2);
                            messageSender.Send(bot, chatId, keyBoardTeleBot);

                        }

                    }

                }catch (IOException e){

                    e.printStackTrace();
                    System.exit(0);

                }

            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });

    }

}
