package UserState;

import java.util.HashMap;

public class UserState {

    private HashMap<Long, States> userStateMap = new HashMap<>();

    public void setUserState (long userId, States state) {
        this.userStateMap.put(userId, state);
    }

    public boolean checkUserState (long userId, States state) {

        boolean check = false;

            if (this.userStateMap.get(userId) == state) {
                check = true;

            }
        return check;
    }

    public boolean emptyUserState (long userId) {

        boolean check = false;
        if (!this.userStateMap.containsKey(userId) || this.userStateMap.get(userId) == null){
            check = true;

        }
        return check;

    }

    public void clearUserState (long userId){
        this.userStateMap.remove(userId);
    }

    public States checkState (long userId){
        return this.userStateMap.get(userId);
    }

}
