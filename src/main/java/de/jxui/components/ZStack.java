package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.Spacers;

import java.awt.*;
import java.util.Arrays;

public class ZStack extends Stack {

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
    public void spacerSize(Size size, Spacers spacers) {
        componentList.forEach(component -> {
            component.spacerSize(size.copy(), spacers);
        });
    }

    @Override
    public void draw(Graphics2D g, Spacers spacers, Point point) {
        for (Component component : componentList) {
            component.draw(g, spacers, new Point(point.getX(), point.getY()));
        }
        point.add(size());
    }
}
