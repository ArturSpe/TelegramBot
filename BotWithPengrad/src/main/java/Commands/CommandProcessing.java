package Commands;
import GettingUserData.DataFromUpdate;
import Messages.MessageSender;
import UserState.*;
import com.pengrad.telegrambot.TelegramBot;


abstract public class CommandProcessing {

        public static void changeStateByCommand(TelegramBot bot, DataFromUpdate updateData, UserState userState, String command) {

            command = command.replaceAll("@" + updateData.botData().username(), "");

        if (BotCommands.thisCommand(command, "/append_word") == true){

            userState.setUserState(updateData.getUserId(), State.checkGroupsWord);

        } else if (BotCommands.thisCommand(command, "/append_phrase") == true) {

            userState.setUserState(updateData.getUserId(), State.checkGroupsPhrase);

        } else if (BotCommands.thisCommand(command, "/end")) {

            MessageSender messageSender = new MessageSender("Бот работает");
            messageSender.Send(bot, updateData.getChatId(), updateData.getMessageId());

            userState.clearUserState(updateData.getUserId());

        }

    }


}
