package Messages;

import java.util.ArrayList;

public abstract class MessageToArray {


    public static ArrayList<String> messageToArrayWithApostrophes (String sentence) {

        String toLowerSentence = sentence.toLowerCase().replaceAll("[^\\p{L}\\s]+", "");
        String[] sentenceToArray = toLowerSentence.split(" ");
        ArrayList<String> stringArrayList = new ArrayList<>(sentenceToArray.length);
        for (String word : sentenceToArray) {

            stringArrayList.add("\"" + word + "\"");

        }

        return stringArrayList;

    }

}
