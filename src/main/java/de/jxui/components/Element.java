package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;

public abstract class Element<T> implements Component, ComponentPadding<T> {

    protected Component component;
    protected Padding padding = null;

    protected Element(@NonNull Component component) {
        this.component = component;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding() {
        padding = new Padding();
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding(Padding padding) {
        this.padding = padding;
        return (T) this;
    }

    @Override
    public Size size() {
        return component.size().add(padding);
    }

    @Override
    public Size actualSize(Graphics2D g, State state) {
        return component.actualSize(g, state).add(padding);
    }

    @Override
    public void spacerSize(Size size, State state) {
        component.spacerSize(size, state);
    }

    @Override
    public int spacers(Orientation orientation) {
        return component.spacers(orientation);
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        component.draw(g, state, point);
    }
}
