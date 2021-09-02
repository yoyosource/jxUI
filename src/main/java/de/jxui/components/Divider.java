package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.Getter;

import java.awt.*;

public class Divider implements Component {

    @Getter
    protected int size;
    private Color color = new Color(0, 0, 0);

    protected Orientation orientation = null;

    public Divider() {
        this.size = -1;
    }

    public Divider(int size) {
        if (size < 0) size = 0;
        this.size = size;
    }

    public Divider color(Color color) {
        this.color = color;
        return this;
    }

    public Divider color(int r, int g, int b) {
        color = new Color(r, g, b);
        return this;
    }

    @Override
    public Size size(UserState userState) {
        if (size == -1) {
            return new Size(0, 0);
        }
        if (orientation == Orientation.VERTICAL) {
            return new Size(size, 0);
        }
        if (orientation == Orientation.HORIZONTAL) {
            return new Size(0, size);
        }
        throw new SecurityException();
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (this.size == -1) {
            Size current = size.copy();
            if (orientation == Orientation.VERTICAL) {
                current.setHeight(0);
            } else if (orientation == Orientation.HORIZONTAL) {
                current.setWidth(0);
            }
            drawState.getSizeMap().put(this, current);
        } else {
            Component.super.size(size, userState, drawState);
        }
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return 0;
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        g.setColor(color);
        Size size = drawState.getSizeMap().get(this).copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.fillRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
        debugDraw(g, drawState, point);
        point.add(drawState.getSizeMap().get(this));
    }
}
