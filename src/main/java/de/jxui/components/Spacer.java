package de.jxui.components;

import de.jxui.utils.*;
import de.jxui.utils.Point;
import lombok.Getter;

import java.awt.*;

public class Spacer implements Component {

    @Getter
    protected int size;

    protected Orientation orientation = null;

    public Spacer() {
        this.size = -1;
    }

    public Spacer(int size) {
        if (size < 0) size = 0;
        this.size = size;
    }

    @Override
    public Size size(UserState userState) {
        if (size == -1) {
            return new Size(0, 0);
        }
        if (orientation == Orientation.HORIZONTAL) {
            return new Size(size, 0);
        }
        if (orientation == Orientation.VERTICAL) {
            return new Size(0, size);
        }
        throw new SecurityException();
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (this.size == -1) {
            Size current = size.copy();
            if (orientation == Orientation.HORIZONTAL) {
                current.setHeight(0);
            } else if (orientation == Orientation.VERTICAL) {
                current.setWidth(0);
            }
            drawState.getSizeMap().put(this, current);
        } else {
            Component.super.size(size, userState, drawState);
        }
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        if (size != -1) return 0;
        return this.orientation == orientation ? 1 : 0;
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        point.add(drawState.getSizeMap().get(this));
    }
}
