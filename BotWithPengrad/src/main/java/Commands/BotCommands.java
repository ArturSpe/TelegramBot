package Commands;

abstract public class BotCommands {

    public static boolean thisCommand (String command, String commandBeingChecked) {
        boolean check = false;
        if (command.equals(commandBeingChecked)) {
            check = true;
        }
        return check;
    }
}
