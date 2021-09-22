package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.components.HStack;
import de.jxui.components.Stack;
import de.jxui.components.VStack;
import de.jxui.components.properties.Joining;
import de.jxui.components.properties.Prefix;
import de.jxui.components.properties.Static;
import de.jxui.components.properties.Suffix;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Render some kind of Grid, with columns and rows.
 */
public class Grid implements Component, Prefix<Grid>, Suffix<Grid>, Joining<Grid>, Static<Grid> {

    private boolean staticRepeat = false;

    private int horizontal;
    private int vertical;
    private BiFunction<Integer, Integer, Component> componentSupplier;

    private Supplier<Component> prefix;
    private Supplier<Component> joining;
    private Supplier<Component> suffix;

    private Stack<?> component = null;

    public Grid(int horizontal, int vertical, @NonNull Supplier<Component> componentSupplier) {
        this(horizontal, vertical, (i, j) -> componentSupplier.get());
    }

    public Grid(int horizontal, int vertical, @NonNull BiFunction<Integer, Integer, Component> componentSupplier) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.componentSupplier = componentSupplier;
    }

    @Override
    public Grid Prefix(Supplier<Component> component) {
        this.prefix = component;
        return this;
    }

    @Override
    public Grid Joining(Supplier<Component> component) {
        this.joining = component;
        return this;
    }

    @Override
    public Grid Suffix(Supplier<Component> component) {
        this.suffix = component;
        return this;
    }

    @Override
    public Grid Static() {
        staticRepeat = true;
        return this;
    }

    @Override
    public Grid Dynamic() {
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
            HStack hStack = new HStack();
            if (prefix != null) {
                hStack.add(prefix.get());
            }
            for (int h = 0; h < horizontal; h++) {
                if (h != 0) {
                    hStack.add(joining.get());
                }
                VStack vStack = new VStack();
                hStack.add(vStack);
                if (prefix != null) {
                    vStack.add(prefix.get());
                }
                for (int v = 0; v < vertical; v++) {
                    if (v != 0) {
                        vStack.add(joining.get());
                    }
                    vStack.add(componentSupplier.apply(h, v));
                }
                if (suffix != null) {
                    vStack.add(suffix.get());
                }
            }
            if (suffix != null) {
                hStack.add(suffix.get());
            }
            component = hStack;
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
