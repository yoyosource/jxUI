package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.Getter;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ValueComponent<T> implements Component {

    @Getter
    private T value;

    private Component component;
    private boolean regen = false;

    private BiFunction<T, ValueComponent<T>, Component> componentCreator;

    public ValueComponent(Function<T, Component> componentCreator) {
        this((t, tValueComponent) -> componentCreator.apply(t));
    }

    public ValueComponent(BiFunction<T, ValueComponent<T>, Component> componentCreator) {
        this(componentCreator, null);
    }

    public ValueComponent(Function<T, Component> componentCreator, T defaultValue) {
        this((t, tValueComponent) -> componentCreator.apply(t), defaultValue);
    }

    public ValueComponent(BiFunction<T, ValueComponent<T>, Component> componentCreator, T defaultValue) {
        this.componentCreator = componentCreator;
        this.value = defaultValue;
    }

    public ValueComponent<T> setValue(T value) {
        this.value = value;
        regen = true;
        return this;
    }

    @Override
    public void cleanUp() {
        if (component != null) {
            component.cleanUp();
        }
    }

    @Override
    public Size size(UserState userState) {
        if (component == null || regen) {
            component = componentCreator.apply(value, this);
            regen = false;
        }
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (component == null) {
            size(userState);
        }
        component.size(size, userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        if (component == null) {
            size(userState);
        }
        return component.spacers(userState, orientation);
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (component == null) {
            size(userState);
        }
        component.event(userState, drawState, point, event);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component == null) {
            size(userState);
        }
        component.draw(g, userState, drawState, point);
    }
}
