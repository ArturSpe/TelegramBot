package GettingUserData;

import com.pengrad.telegrambot.model.Update;

abstract public class GetMessageId {

    private static int messageId;

    public static int getMessageId(Update update){

        messageId = 0;

        if (update != null){

            if (update.message() != null){

                messageId = update.message().messageId();

            } else if (update.callbackQuery() != null) {

                messageId = update.callbackQuery().message().messageId();

            }

        }

        return messageId;
    }

}
