package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.components.*;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.Function;

public class ComponentList<T> implements Component, Prefix<ComponentList<T>>, Suffix<ComponentList<T>> {

    private Function<T, Component> componentFunction;
    private java.util.List<T> list;

    private Orientation orientation = Orientation.VERTICAL;
    private Stack<?> component;

    private Component prefix = null;
    private Component suffix = null;

    public ComponentList(@NonNull Function<T, Component> componentFunction, @NonNull java.util.List<T> list) {
        this.componentFunction = componentFunction;
        this.list = list;
    }

    public ComponentList(@NonNull Orientation orientation, @NonNull Function<T, Component> componentFunction, @NonNull java.util.List<T> list) {
        this.orientation = orientation;
        this.componentFunction = componentFunction;
        this.list = list;
    }

    @Override
    public ComponentList<T> Prefix(Component component) {
        this.prefix = component;
        return this;
    }

    @Override
    public ComponentList<T> Suffix(Component component) {
        this.suffix = component;
        return this;
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            if (orientation == Orientation.VERTICAL) {
                component = new VStack();
            } else {
                component = new HStack();
            }
            if (prefix != null) {
                component.add(prefix);
            }
            list.stream().map(componentFunction).forEach(component::add);
            if (suffix != null) {
                component.add(suffix);
            }
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
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component != null) {
            component.draw(g, userState, drawState, point);
        }
        component = null;
    }
}
