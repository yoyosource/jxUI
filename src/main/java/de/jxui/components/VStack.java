package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class VStack extends Stack<VStack> {

    public VStack(List<Component> components) {
        super(Size::mergeHeight);
        components.forEach(this::add);
    }

    public VStack(Component... components) {
        this(Arrays.asList(components));
    }

    public VStack add(Component component) {
        if (component instanceof Spacer) {
            ((Spacer) component).orientation = Orientation.VERTICAL;
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        sizeImpl(size, userState, drawState, Orientation.VERTICAL);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(userState, orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        Point current = new Point(point.getX(), point.getY()).add(offset);
        for (Component component : componentList) {
            Point uses = current.copy();
            component.event(userState, drawState, uses, event);
            current.setY(uses.getY());
        }
        point.add(drawState.getSizeMap().get(this));
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        Point current = new Point(point.getX(), point.getY());
        current.add(offset);
        for (Component component : componentList) {
            Point uses = current.copy();
            component.draw(g, userState, drawState, uses);
            current.setY(uses.getY());
        }
        point.add(drawState.getSizeMap().get(this));
    }
}
