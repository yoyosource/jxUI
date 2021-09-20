package de.jxui.components.eventcompounds;

import de.jxui.behaviour.Action;
import de.jxui.components.Group;
import de.jxui.components.StateComponent;
import de.jxui.components.Text;
import de.jxui.components.TextTemplate;
import de.jxui.components.compounds.If;
import de.jxui.components.event.Button;
import de.jxui.components.event.Keyboard;
import de.jxui.events.KeyTypeEvent;
import de.jxui.utils.ObjectUtils;
import lombok.NonNull;
import lombok.experimental.ExtensionMethod;

import java.awt.*;
import java.awt.event.KeyEvent;

@ExtensionMethod(ObjectUtils.class)
public class Input extends Group<Input> {

    private TextTemplate textTemplate;
    private Text defaultTextComponent;
    private String defaultText = null;

    public Input(@NonNull String fieldName, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, InputType.TEXT, submitAction);
    }

    public Input(@NonNull String fieldName, @NonNull InputType inputType, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, inputType.keyTypeFunction.apply(fieldName), submitAction);
    }

    public Input(@NonNull String fieldName, @NonNull Action<KeyTypeEvent> keyTypeEventAction, @NonNull Action<KeyTypeEvent> submitAction) {
        textTemplate = new TextTemplate("{" + fieldName + "|''}");
        defaultTextComponent = new Text("");
        component = new Submit(Action.Chain(
                (userState, event) -> {
                    userState.put("@input", null);
                    return true;
                }, submitAction),
                new Keyboard(
                        (userState, event) -> {
                            if (userState.get("@input").equals(fieldName)) {
                                if (event.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
                                    userState.put("@input", null);
                                    return true;
                                }
                                return keyTypeEventAction.run(userState, event);
                            }
                            return false;
                        },
                        new Button(
                                Action.Set("@input", fieldName),
                                new If(
                                        userState -> defaultText == null || userState.getOrDefault("@input", null).equals(fieldName),
                                        textTemplate
                                ).Else(
                                        new StateComponent<>(
                                                defaultTextComponent,
                                                (userState, text) -> text.setText(defaultText)
                                        )
                                )
                        )
                )
        );
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
        defaultTextComponent.minSize(width, height);
        return this;
    }

    public Input setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        return this;
    }

    public Input setDefaultTextColor(Color color) {
        defaultTextComponent.setColor(color);
        return this;
    }

    public Input defaultTextSize(int size) {
        defaultTextComponent.size(size);
        return this;
    }

    public Input defaultTextStyle(int style) {
        defaultTextComponent.style(style);
        return this;
    }

    public Input defaultTextFont(String font) {
        defaultTextComponent.font(font);
        return this;
    }

    public Input setDefaultTextFont(Font font) {
        defaultTextComponent.setFont(font);
        return this;
    }

    public Input defaultTextColor(Color color) {
        defaultTextComponent.color(color);
        return this;
    }

    public Input defaultTextColor(int r, int g, int b) {
        defaultTextComponent.color(r, g, b);
        return this;
    }
}
