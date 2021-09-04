package de.jxui.events;

import lombok.Getter;
import lombok.ToString;

import java.awt.event.KeyEvent;

@Getter
@ToString
public class KeyTypeEvent implements Event {

    private char keyChar;
    private int keyCode;
    private int extendedKeyCode;
    private int modifier;

    public KeyTypeEvent(KeyEvent keyEvent) {
        if (keyEvent.getID() != KeyEvent.KEY_TYPED) {
            throw new SecurityException();
        }
        this.keyChar = keyEvent.getKeyChar();
        this.keyCode = keyEvent.getKeyCode();
        this.extendedKeyCode = keyEvent.getExtendedKeyCode();
        this.modifier = keyEvent.getModifiersEx();
    }
}
