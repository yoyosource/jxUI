package de.jxui.components.eventcompounds;

import de.jxui.behaviour.Action;
import de.jxui.components.Component;
import de.jxui.components.event.Keyboard;
import de.jxui.events.KeyTypeEvent;

public class Submit extends Keyboard {
    public Submit(Action<KeyTypeEvent> keyTypeAction, Component component) {
        super((userState, event) -> {
            if (event.getKeyChar() == '\n') {
                return keyTypeAction.run(userState, event);
            }
            return false;
        }, component);
    }
}
