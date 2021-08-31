package de.jxui.components;

import de.jxui.utils.*;
import de.jxui.utils.Point;

import java.awt.*;
import java.util.Arrays;

public class ZStack extends Stack implements ComponentPadding<ZStack> {

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

    public ZStack padding() {
        padding = new Padding();
        return this;
    }

    public ZStack padding(Padding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public void spacerSize(Size size, State state) {
        componentList.forEach(component -> {
            component.spacerSize(size.copy(), state);
        });
    }

    @Override
    public int spacers(Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        for (Component component : componentList) {
            component.draw(g, state, new Point(point.getX(), point.getY()));
        }
        point.add(actualSize(g, state));
    }
}
