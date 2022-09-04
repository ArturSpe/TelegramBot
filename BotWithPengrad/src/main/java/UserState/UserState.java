package UserState;

import java.io.IOException;
import java.util.HashMap;

public class UserState {

    private HashMap<Long, State> userStateMap = new HashMap<>();

    public void setUserState (long userId, State state) {

        this.userStateMap.put(userId, state);

    }

    public boolean checkUserState (long userId, State state) {

        boolean check = false;

            if (this.userStateMap.get(userId) == state) {

                check = true;

            }

        return check;

    }

    public boolean emptyUserState (long userId) {

        boolean check = false;
        if (this.userStateMap.containsKey(userId) == false || this.userStateMap.get(userId) == null){
            check = true;
        }
        return check;

    }

    public void clearUserState (long userId){

        this.userStateMap.remove(userId);

    }

    public State checkState (long userId){

        return this.userStateMap.get(userId);

    }

}
