package de.jxui.action;

import de.jxui.events.Event;
import de.jxui.utils.ObjectUtils;
import de.jxui.utils.UserState;
import lombok.NonNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Action<T extends Event> {
    boolean run(UserState userState, T event);

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Check(Predicate<K> predicate, Action<K> action) {
        return (T) (Action<K>) (userState, event) -> predicate.test(event) && action.run(userState, event);
    }

    static <K extends Event, T extends Action<K>> T Check(String key, @NonNull Object value, Action<K> action) {
        return Action.Check(key, o -> ObjectUtils.equals(o, value), action);
    }

    @SuppressWarnings("unchecked")
    static <P, K extends Event, T extends Action<K>> T Check(String key, @NonNull Predicate<P> predicate, Action<K> action) {
        return (T) (Action<K>) (userState, event) -> {
            if (!userState.containsKey(key)) {
                return false;
            }
            if (!predicate.test(userState.get(key))) {
                return false;
            }
            return action.run(userState, event);
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Chain(Action<K>... actions) {
        return (T) (Action<K>) (userState, event) -> {
            for (Action<K> action : actions) {
                if (!action.run(userState, event)) {
                    return false;
                }
            }
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, byte increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (byte) userState.getOrDefault(key, (byte) 0) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, char increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (char) userState.getOrDefault(key, '\u0000') + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, short increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (short) userState.getOrDefault(key, (short) 0) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, int increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (int) userState.getOrDefault(key, 0) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, long increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (long) userState.getOrDefault(key, 0L) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, float increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (float) userState.getOrDefault(key, 0F) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T NumberIncrement(String key, double increment) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, (double) userState.getOrDefault(key, 0D) + increment);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T BooleanToggle(String key) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, !(boolean) userState.getOrDefault(key, false));
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T StateToggle(String key, @NonNull Object first, @NonNull Object second) {
        return (T) (Action<K>) (userState, event) -> {
            if (userState.get(key).equals(second)) {
                userState.put(key, first);
            } else {
                userState.put(key, second);
            }
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Set(String key, Object value) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, value);
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Set(String key, Function<UserState, Object> valueFunction) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, valueFunction.apply(userState));
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Set(String key, BiFunction<UserState, K, Object> valueFunction) {
        return (T) (Action<K>) (userState, event) -> {
            userState.put(key, valueFunction.apply(userState, event));
            return true;
        };
    }

    @SuppressWarnings("unchecked")
    static <K extends Event, T extends Action<K>> T Remove(String... keys) {
        return (T) (Action<K>) (userState, event) -> {
            for (String s : keys) {
                userState.remove(s);
            }
            return true;
        };
    }
}
