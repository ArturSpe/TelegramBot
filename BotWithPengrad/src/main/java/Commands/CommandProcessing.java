package Commands;
import GettingUserData.DataFromUpdate;
import Messages.MessageSender;
import UserState.*;
import com.pengrad.telegrambot.TelegramBot;


abstract public class CommandProcessing {

        public static void changeStateByCommand(TelegramBot bot, DataFromUpdate updateData, UserState userState, String command) {

            if (command != null) {
                command = command.replaceAll("@" + updateData.botData().username(), "");

                if (BotCommands.thisCommand(command, "/append_word")) {
                    userState.setUserState(updateData.getUserId(), States.checkGroupsWord);

                } else if (BotCommands.thisCommand(command, "/append_phrase")) {
                    userState.setUserState(updateData.getUserId(), States.checkGroupsPhrase);

                } else if (BotCommands.thisCommand(command, "/end")) {
                    MessageSender messageSender = new MessageSender("Бот работает");
                    messageSender.Send(bot, updateData.getChatId(), updateData.getMessageId());
                    userState.clearUserState(updateData.getUserId());
                }

            }

        }

}
