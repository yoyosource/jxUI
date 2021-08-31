package de.jxui.components;

import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.Spacers;
import lombok.Getter;

import java.awt.*;

public class Spacer implements Component {

    @Getter
    private int size;

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
    public void draw(Graphics2D g, Spacers spacers, Point point) {
        if (orientation == Orientation.HORIZONTAL) {
            point.addX(spacers.getHorizontalSpacers().getOrDefault(this, size));
        } else if (orientation == Orientation.VERTICAL) {
            point.addY(spacers.getVerticalSpacers().getOrDefault(this, size));
        } else {
            throw new SecurityException();
        }
    }
}
