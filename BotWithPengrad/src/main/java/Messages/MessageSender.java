package Messages;

import Keyboards.InlineKeyBoardTeleBot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class MessageSender {

    private TelegramBot bot;
    private String message;
    SendMessage message1;



    public MessageSender (String message) {
        this.message = message;
    }

    public SendResponse Send (TelegramBot bot, long chatId){
        this.message1 = new SendMessage(chatId, this.message);
        return bot.execute(message1);

    }

    public void Send (TelegramBot bot, long chatId, int messageId){
        this.message1 = new SendMessage(chatId, this.message);
        bot.execute(message1.replyToMessageId(messageId));

    }

    public void Send (TelegramBot bot, long chatId, InlineKeyBoardTeleBot keyboardMarkup){
        this.message1 = new SendMessage(chatId, this.message);
        bot.execute(message1.replyMarkup(keyboardMarkup.getKeyboard()));

    }

}
