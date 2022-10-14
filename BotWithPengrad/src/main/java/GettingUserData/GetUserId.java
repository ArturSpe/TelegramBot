package GettingUserData;

import com.pengrad.telegrambot.model.Update;

abstract public class GetUserId {

    private static long userId;

    public static long getUserId(Update update){

        if (update != null){
            if (update.message() != null){
                userId = update.message().from().id();

            } else if (update.callbackQuery() != null) {
                userId = update.callbackQuery().message().from().id();
            }
        }
        return userId;
    }
}
