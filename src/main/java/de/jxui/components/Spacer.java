package de.jxui.components;

import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.State;
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
    public Size actualSize(Graphics2D g, State state) {
        if (orientation == Orientation.HORIZONTAL) {
            return new Size(state.getHorizontalSpacers().getOrDefault(this, size), 0);
        } else if (orientation == Orientation.VERTICAL) {
            return new Size(0, state.getVerticalSpacers().getOrDefault(this, size));
        } else {
            throw new SecurityException();
        }
    }

    @Override
    public int spacers(Orientation orientation) {
        return this.orientation == orientation ? 1 : 0;
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        if (orientation == Orientation.HORIZONTAL) {
            point.addX(state.getHorizontalSpacers().getOrDefault(this, size));
        } else if (orientation == Orientation.VERTICAL) {
            point.addY(state.getVerticalSpacers().getOrDefault(this, size));
        } else {
            throw new SecurityException();
        }
    }
}
