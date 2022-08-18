package Commands;
import Messages.MessageSender;
import UserState.*;
import com.pengrad.telegrambot.TelegramBot;

import java.io.IOException;

abstract public class CommandProcessing {

        public static void changeStateByCommand(TelegramBot bot, long chatId, long userId,  int messageId, UserState userState, String command) throws IOException {

        if (BotCommands.thisCommand(command, "/append_word") == true){

            userState.setUserState(userId, State.Check_groups_word);

        } else if (BotCommands.thisCommand(command, "/append_phrase") == true) {

            userState.setUserState(userId, State.Check_groups_phrase);

        } else if (BotCommands.thisCommand(command, "/end")) {

            MessageSender messageSender = new MessageSender("Бот работает");
            messageSender.Send(bot, chatId, messageId);

            userState.clearUserState(userId);

        }

    }


}
