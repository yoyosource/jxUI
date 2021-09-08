package de.jxui.components.eventcompounds;

import de.jxui.action.Action;
import de.jxui.components.Group;
import de.jxui.components.HStack;
import de.jxui.components.Spacer;
import de.jxui.components.compounds.AbsoluteSize;
import de.jxui.events.KeyTypeEvent;
import lombok.NonNull;

import java.awt.*;

public class AbsoluteInput extends Group<AbsoluteInput> {

    private Input input;

    public AbsoluteInput(@NonNull String fieldName, int width, int height, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, width, height, InputType.TEXT, submitAction);
    }

    public AbsoluteInput(@NonNull String fieldName, int width, int height, @NonNull InputType inputType, @NonNull Action<KeyTypeEvent> submitAction) {
        this(fieldName, width, height, inputType.keyTypeFunction.apply(fieldName), submitAction);
    }

    public AbsoluteInput(@NonNull String fieldName, int width, int height, @NonNull Action<KeyTypeEvent> keyTypeEventAction, @NonNull Action<KeyTypeEvent> submitAction) {
        input = new Input(fieldName, keyTypeEventAction, submitAction).minSize(width, height);
        component = new AbsoluteSize(width, height,
                new HStack(
                        new Spacer(),
                        input
                )
        );
    }

    public AbsoluteInput setColor(Color color) {
        input.setColor(color);
        return this;
    }

    public AbsoluteInput size(int size) {
        input.size(size);
        return this;
    }

    public AbsoluteInput style(int style) {
        input.style(style);
        return this;
    }

    public AbsoluteInput font(String name) {
        input.font(name);
        return this;
    }

    public AbsoluteInput setFont(Font font) {
        input.setFont(font);
        return this;
    }

    public AbsoluteInput color(Color color) {
        input.color(color);
        return this;
    }

    public AbsoluteInput color(int r, int g, int b) {
        input.color(r, g, b);
        return this;
    }

    public AbsoluteInput minSize(int width, int height) {
        input.minSize(width, height);
        return this;
    }

    public AbsoluteInput setDefaultText(String defaultText) {
        input.setDefaultText(defaultText);
        return this;
    }

    public AbsoluteInput setDefaultTextColor(Color color) {
        input.setDefaultTextColor(color);
        return this;
    }

    public AbsoluteInput defaultTextSize(int size) {
        input.defaultTextSize(size);
        return this;
    }

    public AbsoluteInput defaultTextStyle(int style) {
        input.defaultTextStyle(style);
        return this;
    }

    public AbsoluteInput defaultTextFont(String font) {
        input.defaultTextFont(font);
        return this;
    }

    public AbsoluteInput setDefaultTextFont(Font font) {
        input.setDefaultTextFont(font);
        return this;
    }

    public AbsoluteInput defaultTextColor(Color color) {
        input.defaultTextColor(color);
        return this;
    }

    public AbsoluteInput defaultTextColor(int r, int g, int b) {
        input.defaultTextColor(r, g, b);
        return this;
    }
}
