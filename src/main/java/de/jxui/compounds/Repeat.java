package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.components.*;
import de.jxui.components.behaviour.Joining;
import de.jxui.components.behaviour.Prefix;
import de.jxui.components.behaviour.Static;
import de.jxui.components.behaviour.Suffix;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Repeat implements Component, Prefix<Repeat>, Suffix<Repeat>, Joining<Repeat>, Static<Repeat> {

    private boolean staticRepeat = false;

    private Orientation orientation = Orientation.VERTICAL;
    private int count;
    private Function<Integer, Component> componentSupplier;

    private Supplier<Component> prefix;
    private Supplier<Component> joining;
    private Supplier<Component> suffix;

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
    public Repeat Prefix(Supplier<Component> component) {
        this.prefix = component;
        return this;
    }

    @Override
    public Repeat Joining(Supplier<Component> component) {
        this.joining = component;
        return this;
    }

    @Override
    public Repeat Suffix(Supplier<Component> component) {
        this.suffix = component;
        return this;
    }

    @Override
    public Repeat Static() {
        staticRepeat = true;
        return this;
    }

    @Override
    public Repeat Dynamic() {
        staticRepeat = false;
        return this;
    }

    @Override
    public void cleanUp() {
        if (component != null) {
            component.cleanUp();
        }
        if (!staticRepeat) {
            component = null;
        }
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
                component.add(prefix.get());
            }
            for (int i = 0; i < count; i++) {
                if (i != 0) {
                    component.add(joining.get());
                }
                component.add(componentSupplier.apply(i));
            }
            if (suffix != null) {
                component.add(suffix.get());
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
    }
}
