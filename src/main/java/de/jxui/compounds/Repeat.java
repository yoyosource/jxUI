package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.components.*;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Repeat implements Component, Prefix<Repeat>, Suffix<Repeat>, Joining<Repeat> {

    private Orientation orientation = Orientation.VERTICAL;
    private int count;
    private Function<Integer, Component> componentSupplier;

    private Component prefix;
    private Component joining;
    private Component suffix;

    private Stack<?> component = null;

    public Repeat(int count, @NonNull Supplier<Component> componentSupplier) {
        this(count, i -> componentSupplier.get());
    }

    public Repeat(int count, @NonNull Function<Integer, Component> componentSupplier) {
        this.count = count;
        this.componentSupplier = componentSupplier;
    }

    public Repeat(@NonNull Orientation orientation, int count, @NonNull Supplier<Component> componentSupplier) {
        this(orientation, count, i -> componentSupplier.get());
    }

    public Repeat(@NonNull Orientation orientation, int count, @NonNull Function<Integer, Component> componentSupplier) {
        this.orientation = orientation;
        this.count = count;
        this.componentSupplier = componentSupplier;
    }

    @Override
    public Repeat Prefix(Component component) {
        this.prefix = component;
        return this;
    }

    @Override
    public Repeat Joining(Component component) {
        this.joining = component;
        return this;
    }

    @Override
    public Repeat Suffix(Component component) {
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
            for (int i = 0; i < count; i++) {
                if (i != 0) {
                    component.add(joining);
                }
                component.add(componentSupplier.apply(i));
            }
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
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (component == null) {
            size(userState);
        }
        component.event(userState, drawState, point, event);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component != null) {
            component.draw(g, userState, drawState, point);
        }
        component = null;
    }
}
