package Keyboards;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import java.util.ArrayList;

public class InlineKeyBoardTeleBot{

    private InlineKeyboardMarkup keyboard;


    public InlineKeyBoardTeleBot (String[] buttonText, int numberOfButtonsInRow) {

        int n = numberOfButtonsInRow;
        int numberOfIterations = 0;
        int numberLines = buttonText.length / n;
        if (buttonText.length % n != 0){numberLines++;};
        InlineKeyboardButton[][] keyboardButtons = new InlineKeyboardButton[numberLines][];

        for (int i = 0; i < buttonText.length; i += n) {

            ArrayList<InlineKeyboardButton> buttonsInLIneArrayList = new ArrayList<InlineKeyboardButton>();
            double nn = (double) n;
            double iterationCheck = (buttonText.length - i) / nn;

            if (iterationCheck <= 1) {
                for (int j = i; j <= buttonText.length - 1; j++) {
                    InlineKeyboardButton button = new InlineKeyboardButton(buttonText[j]).callbackData(buttonText[j]);
                    buttonsInLIneArrayList.add(button);
                }
            } else if (iterationCheck > 1) {
                for (int j = i; j <= i + n - 1; j++) {
                    InlineKeyboardButton button = new InlineKeyboardButton(buttonText[j]).callbackData(buttonText[j]);
                    buttonsInLIneArrayList.add(button);
                }
            }
            InlineKeyboardButton [] buttonsInLineArray = buttonsInLIneArrayList.toArray(new InlineKeyboardButton[0]);
            keyboardButtons[numberOfIterations] = buttonsInLineArray;
            numberOfIterations++;
        }
        this.keyboard = new InlineKeyboardMarkup(keyboardButtons);
    }

    public InlineKeyboardMarkup getKeyboard() {
        return this.keyboard;
    }

}
