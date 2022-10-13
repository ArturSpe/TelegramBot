package GettingUserData;

import com.pengrad.telegrambot.model.Update;

abstract public class GetChatId {

    private static long chatId;

    public static long getChatId(Update update){

        if (update != null){
            if (update.message() != null){
                chatId = update.message().chat().id();

            } else if (update.callbackQuery() != null) {
                chatId = update.callbackQuery().message().chat().id();
            }
        }
        return chatId;
    }
}
