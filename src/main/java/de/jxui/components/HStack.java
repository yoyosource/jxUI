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
public class HStack extends Stack<HStack> {

    public HStack(List<Component> components) {
        super(Size::mergeWidth);
        components.forEach(this::add);
    }

    public HStack(Component... components) {
        this(Arrays.asList(components));
    }

    public HStack add(Component component) {
        if (component instanceof Spacer) {
            ((Spacer) component).orientation = Orientation.HORIZONTAL;
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        Size currentSize = size(userState);
        Size spacerSize = size.copy().substract(currentSize);

        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(userState, Orientation.HORIZONTAL) - spacerList.size() > 0 ? 1 : 0);

        log.debug("Spacer: {}   Splitting: {}   Size: {}   AllowedSize: {}", spacerList.size(), splitSize, currentSize, size);
        SpacerCalculation spacerCalculation = new SpacerCalculation(spacerSize.getWidth(), splitSize);
        for (Spacer spacer : spacerList) {
            spacer.size(size.copy().setWidth(spacerCalculation.next()), userState, drawState);
        }

        Set<Component> components = componentList.stream().filter(component -> !drawState.getSizeMap().containsKey(component)).filter(component -> component.spacers(userState, Orientation.HORIZONTAL) > 0).collect(Collectors.toSet());
        int componentSplitSize = spacerSize.getWidth() / (splitSize == 0 ? 1 : splitSize);
        spacerCalculation = new SpacerCalculation(componentSplitSize, components.size());
        log.debug("Other Component sizes: {}   Components: {}", componentSplitSize, components.size());
        for (Component component : componentList) {
            if (drawState.getSizeMap().containsKey(component)) {
                continue;
            }
            Size current = component.size(userState);
            current.setHeight(size.getHeight());
            if (components.contains(component)) {
                current.setWidth(current.getWidth() + spacerCalculation.next());
            }
            component.size(current, userState, drawState);
        }
        drawState.getSizeMap().put(this, size);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(userState, orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        Point current = new Point(point.getX(), point.getY());
        current.add(offset);
        for (Component component : componentList) {
            Point uses = current.copy();
            component.event(userState, drawState, uses, event);
            current.setX(uses.getX());
        }
        point.add(drawState.getSizeMap().get(this));
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        Point current = new Point(point.getX(), point.getY()).add(offset);
        for (Component component : componentList) {
            Point uses = current.copy();
            component.draw(g, userState, drawState, uses);
            current.setX(uses.getX());
        }
        point.add(drawState.getSizeMap().get(this));
    }
}
