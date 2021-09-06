package de.jxui.action;

import de.jxui.events.KeyTypeEvent;
import lombok.experimental.UtilityClass;

import java.awt.event.KeyEvent;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@UtilityClass
public class KeyTypeAction {
    public Action<KeyTypeEvent> Text(String key) {
        return (userState, event) -> {
            String current = userState.getOrDefault(key, "");
            if (event.getExtendedKeyCode() == KeyEvent.VK_DELETE || event.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (current.length() > 0) {
                    userState.put(key, current.substring(0, current.length() - 1));
                }
            } else {
                userState.put(key, current + event.getKeyChar());
            }
            return false;
        };
    }

    public Action<KeyTypeEvent> Number(String key) {
        return Number(key, (s, c) -> c >= '0' && c <= '9');
    }

    public Action<KeyTypeEvent> Number(String key, BiPredicate<String, Character> checkPredicate) {
        return (userState, event) -> {
            String current = userState.getOrDefault(key, "");
            if (event.getExtendedKeyCode() == KeyEvent.VK_DELETE || event.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (current.length() > 0) {
                    userState.put(key, current.substring(0, current.length() - 1));
                }
                return true;
            }
            current = current + event.getKeyChar();
            if (checkPredicate.test(current, event.getKeyChar())) {
                userState.put(key, current);
                return true;
            }
            return false;
        };
    }

    public Action<KeyTypeEvent> Byte(String key) {
        return Byte(key, 10, value -> true);
    }

    public Action<KeyTypeEvent> Byte(String key, Predicate<Byte> predicate) {
        return Byte(key, 10, predicate);
    }

    public Action<KeyTypeEvent> Byte(String key, int radix) {
        return Byte(key, radix, value -> true);
    }

    public Action<KeyTypeEvent> Byte(String key, int radix, Predicate<Byte> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Byte.parseByte(s, radix));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Action<KeyTypeEvent> Short(String key) {
        return Short(key, 10, value -> true);
    }

    public Action<KeyTypeEvent> Short(String key, Predicate<Short> predicate) {
        return Short(key, 10, predicate);
    }

    public Action<KeyTypeEvent> Short(String key, int radix) {
        return Short(key, radix, value -> true);
    }

    public Action<KeyTypeEvent> Short(String key, int radix, Predicate<Short> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Short.parseShort(s, radix));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Action<KeyTypeEvent> Int(String key) {
        return Int(key, 10, value -> true);
    }

    public Action<KeyTypeEvent> Int(String key, Predicate<Integer> predicate) {
        return Int(key, 10, predicate);
    }

    public Action<KeyTypeEvent> Int(String key, int radix) {
        return Int(key, radix, value -> true);
    }

    public Action<KeyTypeEvent> Int(String key, int radix, Predicate<Integer> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Integer.parseInt(s, radix));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Action<KeyTypeEvent> Long(String key) {
        return Long(key, 10, value -> true);
    }

    public Action<KeyTypeEvent> Long(String key, Predicate<Long> predicate) {
        return Long(key, 10, predicate);
    }

    public Action<KeyTypeEvent> Long(String key, int radix) {
        return Long(key, radix, value -> true);
    }

    public Action<KeyTypeEvent> Long(String key, int radix, Predicate<Long> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Long.parseLong(s, radix));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Action<KeyTypeEvent> Float(String key) {
        return Float(key, value -> true);
    }

    public Action<KeyTypeEvent> Float(String key, Predicate<Float> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Float.parseFloat(s));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public Action<KeyTypeEvent> Double(String key) {
        return Double(key, value -> true);
    }

    public Action<KeyTypeEvent> Double(String key, Predicate<Double> predicate) {
        return Number(key, (s, character) -> {
            try {
                return predicate.test(Double.parseDouble(s));
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }
}
