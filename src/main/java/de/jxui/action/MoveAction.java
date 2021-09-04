package de.jxui.action;

import de.jxui.events.MouseClickEvent;
import de.jxui.events.MouseMoveEvent;
import de.jxui.utils.UserState;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface MoveAction extends Action<MouseMoveEvent> {
    static MoveAction Set(String key, Object value) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, value);
            }
            return true;
        };
    }

    static MoveAction Set(String key, Function<UserState, Object> valueFunction) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, valueFunction.apply(userState));
            }
            return true;
        };
    }

    static MoveAction Set(String key, BiFunction<UserState, MouseMoveEvent, Object> valueFunction) {
        return (userState, event) -> {
            if (!userState.containsKey(key)) {
                userState.put(key, valueFunction.apply(userState, event));
            }
            return true;
        };
    }

    static MoveAction Remove(String key) {
        return (userState, event) -> {
            userState.remove(key);
            return true;
        };
    }
}
