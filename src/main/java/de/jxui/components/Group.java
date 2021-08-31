package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;

public abstract class Group<T> extends Element<T> {

    protected Component component;

    protected Group(@NonNull Component component) {
        this.component = component;
    }

    @Override
    public Size size() {
        return component.size().add(padding);
    }

    @Override
    public Size actualSize(Graphics2D g, DrawState drawState) {
        return component.actualSize(g, drawState).add(padding);
    }

    @Override
    public void spacerSize(Size size, DrawState drawState) {
        component.spacerSize(size, drawState);
    }

    @Override
    public int spacers(Orientation orientation) {
        return component.spacers(orientation);
    }

    @Override
    public void draw(Graphics2D g, DrawState drawState, Point point) {
        component.draw(g, drawState, point);
    }
}
