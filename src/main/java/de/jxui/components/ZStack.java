package de.jxui.components;

import de.jxui.utils.*;
import de.jxui.utils.Point;

import java.awt.*;
import java.util.Arrays;

public class ZStack extends Stack<ZStack> {

    public ZStack(Component... components) {
        super(Size::merge);
        for (Component component : components) {
            if (component instanceof Spacer) {
                throw new SecurityException();
            }
        }
        componentList.addAll(Arrays.asList(components));
    }

    public ZStack add(Component component) {
        if (component instanceof Spacer) {
            throw new SecurityException();
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void spacerSize(Size size, DrawState drawState) {
        componentList.forEach(component -> {
            component.spacerSize(size.copy(), drawState);
        });
    }

    @Override
    public int spacers(Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void draw(Graphics2D g, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        for (Component component : componentList) {
            Point current = new Point(point.getX(), point.getY());
            current.add(offset);
            component.draw(g, drawState, current);
        }
        point.add(actualSize(g, drawState));
    }
}
