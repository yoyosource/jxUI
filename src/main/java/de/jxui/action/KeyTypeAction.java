package de.jxui.action;

import de.jxui.events.KeyTypeEvent;

import java.awt.event.KeyEvent;

public interface KeyTypeAction extends Action<KeyTypeEvent> {
    static KeyTypeAction Text(String key) {
        return (userState, event) -> {
            String current = userState.getOrDefault(key, "");
            if (event.getExtendedKeyCode() == KeyEvent.VK_DELETE || event.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (current.length() > 0) {
                    userState.put(key, current.substring(0, current.length() - 1));
                }
            } else {
                userState.put(key, current + event.getKeyChar());
            }
            return true;
        };
    }

    static KeyTypeAction Number(String key) {
        return (userState, event) -> {
            String current = userState.getOrDefault(key, "");
            if (event.getExtendedKeyCode() == KeyEvent.VK_DELETE || event.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (current.length() > 0) {
                    userState.put(key, current.substring(0, current.length() - 1));
                }
            } else if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9') {
                userState.put(key, current + event.getKeyChar());
            }
            return true;
        };
    }
}
