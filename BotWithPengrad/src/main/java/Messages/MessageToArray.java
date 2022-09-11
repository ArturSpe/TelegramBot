package Messages;

import java.util.ArrayList;

public abstract class MessageToArray {


    public static ArrayList<String> messageToArrayWithApostrophes (String sentence) {

        ArrayList<String> stringArrayList = new ArrayList<>();

        if (sentence != null) {

            String toLowerSentence = sentence.toLowerCase().replaceAll("[^\\p{L}\\s]+", "");
            String[] sentenceToArray = toLowerSentence.split(" ");
            stringArrayList = new ArrayList<>(sentenceToArray.length);
            for (String word : sentenceToArray) {

                stringArrayList.add("\"" + word + "\"");

            }

        }

        return stringArrayList;

    }

}
