package de.jxui.action;

import de.jxui.events.MouseClickEvent;
import de.jxui.utils.UserState;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ButtonAction extends Action<MouseClickEvent> {
    static ButtonAction NumberIncrement(String key, byte increment) {
        return (userState, event) -> {
            userState.put(key, (byte) userState.getOrDefault(key, (byte) 0) + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, char increment) {
        return (userState, event) -> {
            userState.put(key, (char) userState.getOrDefault(key, '\u0000') + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, short increment) {
        return (userState, event) -> {
            userState.put(key, (short) userState.getOrDefault(key, (short) 0) + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, int increment) {
        return (userState, event) -> {
            userState.put(key, (int) userState.getOrDefault(key, 0) + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, long increment) {
        return (userState, event) -> {
            userState.put(key, (long) userState.getOrDefault(key, 0L) + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, float increment) {
        return (userState, event) -> {
            userState.put(key, (float) userState.getOrDefault(key, 0F) + increment);
            return true;
        };
    }

    static ButtonAction NumberIncrement(String key, double increment) {
        return (userState, event) -> {
            userState.put(key, (double) userState.getOrDefault(key, 0D) + increment);
            return true;
        };
    }

    static ButtonAction BooleanToggle(String key) {
        return (userState, event) -> {
            userState.put(key, !(boolean) userState.getOrDefault(key, false));
            return true;
        };
    }

    static ButtonAction StateToggle(String key, Object first, Object second) {
        return (userState, event) -> {
            if (userState.get(key).equals(second)) {
                userState.put(key, first);
            } else {
                userState.put(key, second);
            }
            return true;
        };
    }

    static ButtonAction SetIfUnset(String key, Object value) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, value);
            }
            return true;
        };
    }

    static ButtonAction SetIfUnset(String key, Function<UserState, Object> valueFunction) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, valueFunction.apply(userState));
            }
            return true;
        };
    }

    static ButtonAction SetIfUnset(String key, BiFunction<UserState, MouseClickEvent, Object> valueFunction) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, valueFunction.apply(userState, event));
            }
            return true;
        };
    }
}
