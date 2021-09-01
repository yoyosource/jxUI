package de.jxui.components;

import de.jxui.JxUI;
import de.jxui.compounds.CenteredStack;
import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.DrawState;

import java.awt.*;

public interface Component {
    Size size();
    default Size actualSize(Graphics2D g, DrawState drawState) {
        return size();
    }
    default void spacerSize(Size size, DrawState drawState) {
    }
    default int spacers(Orientation orientation) {
        return 0;
    }
    default void draw(Graphics2D g, DrawState drawState, Point point) {
    }

    default void debugDraw(Graphics2D g, DrawState drawState, Point point) {
        if (JxUI.DEBUG) {
            if (this instanceof Spacer) {
                g.setColor(Color.BLUE);
            } else if (this instanceof Stack || this instanceof CenteredStack) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.RED);
            }
            Size size = actualSize(g, drawState);
            if (size.getWidth() == 0) size.setWidth(1);
            if (size.getHeight() == 0) size.setHeight(1);
            g.drawRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
        }
    }
}
