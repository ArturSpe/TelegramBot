package GettingUserData;

import com.pengrad.telegrambot.model.Update;

public class UserMetaData {

    private long chatId;
    private int messageId;
    private long userId;

    private Update update;

    public UserMetaData (Update update){

        this.update = update;

        if (this.update != null){

            if (update.message() != null){

                this.chatId = this.update.message().chat().id();

                this.messageId = this.update.message().messageId();

                this.userId = this.update.message().from().id();

            } else if (this.update.callbackQuery() != null) {

                this.chatId = this.update.callbackQuery().message().chat().id();

                this.messageId = this.update.callbackQuery().message().messageId();

                this.userId = this.update.callbackQuery().message().from().id();

            }

        }

    }

    public long getChatId (){

        return this.chatId;

    }

    public int getMessageId () {

        return this.messageId;

    }

    public long getUserId () {

        return this.userId;

    }

}
