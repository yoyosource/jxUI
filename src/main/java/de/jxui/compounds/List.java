package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.components.HStack;
import de.jxui.components.VStack;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class List<T> implements Component {

    private Function<T, Component> componentFunction;
    private java.util.List<T> list;

    private Orientation orientation = Orientation.VERTICAL;
    private Component component;

    public List(@NonNull Function<T, Component> componentFunction, @NonNull java.util.List<T> list) {
        this.componentFunction = componentFunction;
        this.list = list;
    }

    public List(@NonNull Orientation orientation, @NonNull Function<T, Component> componentFunction, @NonNull java.util.List<T> list) {
        this.orientation = orientation;
        this.componentFunction = componentFunction;
        this.list = list;
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            if (orientation == Orientation.VERTICAL) {
                component = new VStack(list.stream().map(componentFunction).collect(Collectors.toList()));
            } else {
                component = new HStack(list.stream().map(componentFunction).collect(Collectors.toList()));
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
