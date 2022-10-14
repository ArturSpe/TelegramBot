package GettingUserData;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;

public class DataFromUpdate {

    private long chatId;
    private int messageId;
    private long userId;

    private Update update;
    private String messageText;
    private String callbackText;
    private GetMeResponse botData;
    public DataFromUpdate(TelegramBot bot, Update update){

        this.update = update;
        this.botData = bot.execute(new GetMe());

        if (this.update != null){

            if (update.message() != null){
                this.chatId = this.update.message().chat().id();
                this.messageId = this.update.message().messageId();
                this.userId = this.update.message().from().id();

                if (update.message().text() != null){
                    this.messageText = update.message().text();
                }

            } else if (this.update.callbackQuery() != null) {
                this.chatId = this.update.callbackQuery().message().chat().id();
                this.messageId = this.update.callbackQuery().message().messageId();
                this.userId = this.update.callbackQuery().from().id();

                if (update.callbackQuery().message().text() != null){
                    this.callbackText = update.callbackQuery().data();
                }
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

    public String getMessageText (){
        return this.messageText;
    }

    public String getCallbackText (){
        return this.callbackText;
    }

    public User botData () {
        return this.botData.user();
    }

}
