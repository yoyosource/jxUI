package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.*;
import de.jxui.utils.Point;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ZStack extends Stack<ZStack> {

    public ZStack(List<Component> components) {
        super(Size::merge);
        components.forEach(this::add);
    }

    public ZStack(Component... components) {
        this(Arrays.asList(components));
    }

    public ZStack add(Component component) {
        if (component instanceof Spacer) {
            throw new SecurityException();
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        componentList.forEach(component -> {
            component.size(size, userState, drawState);
        });
        drawState.getSizeMap().put(this, size);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(userState, orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        for (Component component : componentList) {
            Point current = new Point(point.getX(), point.getY());
            current.add(offset);
            component.event(userState, drawState, current, event);
        }
        point.add(drawState.getSizeMap().get(this));
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        for (Component component : componentList) {
            Point current = point.copy().add(offset);
            component.draw(g, userState, drawState, current);
        }
        point.add(drawState.getSizeMap().get(this));
    }
}
