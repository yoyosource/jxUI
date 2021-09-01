package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
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
    public Size size() {
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
    public Size actualSize(Graphics2D g, DrawState drawState) {
        if (orientation == Orientation.HORIZONTAL) {
            return new Size(drawState.getHorizontalSpacers().getOrDefault(this, size), 0);
        } else if (orientation == Orientation.VERTICAL) {
            return new Size(0, drawState.getVerticalSpacers().getOrDefault(this, size));
        } else {
            throw new SecurityException();
        }
    }

    @Override
    public int spacers(Orientation orientation) {
        if (size != -1) return 0;
        return this.orientation == orientation ? 1 : 0;
    }

    @Override
    public void draw(Graphics2D g, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        if (orientation == Orientation.HORIZONTAL) {
            point.addX(drawState.getHorizontalSpacers().getOrDefault(this, size));
        } else if (orientation == Orientation.VERTICAL) {
            point.addY(drawState.getVerticalSpacers().getOrDefault(this, size));
        } else {
            throw new SecurityException();
        }
    }
}
