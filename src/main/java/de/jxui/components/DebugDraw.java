package de.jxui.components;

import de.jxui.JxUI;
import de.jxui.compounds.CenteredStack;
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
        if (component instanceof Stack || component instanceof CenteredStack) {
            drawStack(g, point, drawState.getSizeMap().get(component));
            return;
        }
        drawOther(g, point, drawState.getSizeMap().get(component));
    }

    private void drawSpacer(Graphics2D g, Point point, Size size) {
        if (size.getWidth() == 0) {
            if (size.getHeight() < 0) {
                g.setColor(Color.BLUE.brighter());
                g.drawRect(point.getX(), point.getY() + size.getHeight(), 1, size.getHeight() * -1);
            } else {
                g.setColor(Color.BLUE);
                g.drawRect(point.getX(), point.getY(), 1, size.getHeight());
            }
        }
        if (size.getHeight() == 0) {
            if (size.getWidth() < 0) {
                g.setColor(Color.BLUE.brighter());
                g.drawRect(point.getX() + size.getWidth(), point.getY(), size.getWidth() * -1, 1);
            } else {
                g.setColor(Color.BLUE);
                g.drawRect(point.getX(), point.getY(), size.getWidth(), 1);
            }
        }
    }

    private void drawStack(Graphics2D g, Point point, Size size) {
        g.setColor(Color.ORANGE);
        size = size.copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.drawRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
    }

    private void drawOther(Graphics2D g, Point point, Size size) {
        g.setColor(Color.RED);
        size = size.copy();
        if (size.getWidth() == 0) size.setWidth(1);
        if (size.getHeight() == 0) size.setHeight(1);
        g.drawRect(point.getX(), point.getY(), size.getWidth(), size.getHeight());
    }
}
