package de.jxui.behaviour;

import de.jxui.utils.UserState;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

@UtilityClass
public class UserStatePredicate {

    public Predicate<UserState> containsAll(String... strings) {
        return userState -> {
            for (String s : strings) {
                if (!userState.containsKey(s)) {
                    return false;
                }
            }
            return true;
        };
    }

    public Predicate<UserState> containsOne(String... strings) {
        return userState -> {
            for (String s : strings) {
                if (userState.containsKey(s)) {
                    return true;
                }
            }
            return false;
        };
    }

    public Predicate<UserState> containsNone(String... strings) {
        return userState -> {
            for (String s : strings) {
                if (userState.containsKey(s)) {
                    return false;
                }
            }
            return true;
        };
    }
}
