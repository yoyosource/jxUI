package de.jxui.components.eventcompounds;

import de.jxui.action.Action;
import de.jxui.action.KeyTypeAction;
import de.jxui.components.Group;
import de.jxui.components.TextTemplate;
import de.jxui.components.event.Button;
import de.jxui.components.event.Keyboard;
import de.jxui.events.KeyTypeEvent;
import de.jxui.utils.Direction;
import de.jxui.utils.ObjectUtils;
import de.jxui.utils.Offset;
import de.jxui.utils.Padding;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.ExtensionMethod;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Function;

@ExtensionMethod(ObjectUtils.class)
public class Input extends Group<Input> {

    private TextTemplate textTemplate;

    @AllArgsConstructor
    public enum InputType {
        TEXT(KeyTypeAction::Text),
        BYTE(KeyTypeAction::Byte),
        SHORT(KeyTypeAction::Short),
        INT(KeyTypeAction::Int),
        LONG(KeyTypeAction::Long),
        FLOAT(KeyTypeAction::Float),
        DOUBLE(KeyTypeAction::Double);

        private Function<String, Action<KeyTypeEvent>> keyTypeFunction;
    }

    public Input(@NonNull String fieldName, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, InputType.TEXT, submitAction);
    }

    public Input(@NonNull String fieldName, @NonNull InputType inputType, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, inputType.keyTypeFunction.apply(fieldName), submitAction);
    }

    public Input(@NonNull String fieldName, @NonNull Action<KeyTypeEvent> keyTypeEventAction, @NonNull Action<KeyTypeEvent> submitAction) {
        textTemplate = new TextTemplate("{" + fieldName + "|''}");
        component = new Submit(Action.Chain((userState, event) -> {
            userState.put("@input", null);
            return true;
        }, submitAction), new Keyboard((userState, event) -> {
            if (userState.get("@input").equals(fieldName)) {
                if (event.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
                    userState.put("@input", null);
                    return true;
                }
                return keyTypeEventAction.run(userState, event);
            }
            return false;
        }, new Button(Action.Set("@input", fieldName), textTemplate)));
    }

    @Override
    public Input padding() {
        textTemplate.padding();
        return this;
    }

    @Override
    public Input padding(@NonNull Padding padding) {
        textTemplate.padding(padding);
        return this;
    }

    @Override
    public Input padding(Direction direction, int value) {
        textTemplate.padding(direction, value);
        return this;
    }

    @Override
    public Input offset() {
        textTemplate.offset();
        return this;
    }

    @Override
    public Input offset(@NonNull Offset offset) {
        textTemplate.offset(offset);
        return this;
    }

    @Override
    public Input offset(Direction direction, int value) {
        textTemplate.offset(direction, value);
        return this;
    }

    public Input setColor(Color color) {
        textTemplate.setColor(color);
        return this;
    }

    public Input size(int size) {
        textTemplate.size(size);
        return this;
    }

    public Input style(int style) {
        textTemplate.style(style);
        return this;
    }

    public Input font(String name) {
        textTemplate.font(name);
        return this;
    }

    public Input setFont(Font font) {
        textTemplate.setFont(font);
        return this;
    }

    public Input color(Color color) {
        textTemplate.color(color);
        return this;
    }

    public Input color(int r, int g, int b) {
        textTemplate.color(r, g, b);
        return this;
    }

    public Input minSize(int width, int height) {
        textTemplate.minSize(width, height);
        return this;
    }
}
