package de.jxui.components.eventcompounds;

import de.jxui.behaviour.Action;
import de.jxui.behaviour.KeyTypeAction;
import de.jxui.events.KeyTypeEvent;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public enum InputType {
    TEXT(KeyTypeAction::Text),
    SINGLE_LINE_TEXT(KeyTypeAction::SingleLineText),
    BYTE(KeyTypeAction::Byte),
    SHORT(KeyTypeAction::Short),
    INT(KeyTypeAction::Int),
    LONG(KeyTypeAction::Long),
    FLOAT(KeyTypeAction::Float),
    DOUBLE(KeyTypeAction::Double);

    Function<String, Action<KeyTypeEvent>> keyTypeFunction;
}
