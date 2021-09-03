package de.jxui.components;

import de.jxui.compounds.Button;
import de.jxui.compounds.Centered;
import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
class DebugDraw {

    static void drawDebug(Component component, Graphics2D g, DrawState drawState, Point point) {
        if (!drawState.isDebug()) return;

        if (component instanceof Spacer) {
            drawSpacer(g, point, drawState.getSizeMap().get(component));
            return;
        }
        if (component instanceof Stack || component instanceof Centered) {
            drawStack(g, point, drawState.getSizeMap().get(component));
            return;
        }
        if (component instanceof Button) {
            drawButton(g, point, drawState.getSizeMap().get(component));
            return;
        }
        drawOther(g, point, drawState.getSizeMap().get(component));
    }

    private void drawSpacer(Graphics2D g, Point point, Size size) {
        if (size.getWidth() == 0) {
            if (size.getHeight() < 0) {
                g.setColor(Color.GRAY);
                g.drawRect(point.getX(), point.getY() + size.getHeight(), 1, size.getHeight() * -1);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(point.getX(), point.getY(), 1, size.getHeight());
            }
            g.drawRect(point.getX() - 2, point.getY() + size.getHeight(), 5, 1);
            g.drawRect(point.getX() - 2, point.getY(), 5, 1);
        }
        if (size.getHeight() == 0) {
            if (size.getWidth() < 0) {
                g.setColor(Color.GRAY);
                g.drawRect(point.getX() + size.getWidth(), point.getY(), size.getWidth() * -1, 1);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(point.getX(), point.getY(), size.getWidth(), 1);
            }
            g.drawRect(point.getX() + size.getWidth(), point.getY() - 2, 1, 5);
            g.drawRect(point.getX(), point.getY() - 2, 1, 5);
        }
    }

    private void drawStack(Graphics2D g, Point point, Size size) {
        g.setColor(Color.ORANGE);
        size = size.copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.drawRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
    }

    private void drawButton(Graphics2D g, Point point, Size size) {
        g.setColor(new Color(128, 128, 128, 128));
        size = size.copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.fillRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
    }

    private void drawOther(Graphics2D g, Point point, Size size) {
        g.setColor(Color.RED);
        size = size.copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.drawRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
    }
}
