package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VStack extends Stack<VStack> {

    public VStack(Component... components) {
        super(Size::mergeHeight);
        for (Component component : components) {
            add(component);
        }
    }

    public VStack add(Component component) {
        if (component instanceof Spacer) {
            ((Spacer) component).orientation = Orientation.VERTICAL;
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void spacerSize(Size size, DrawState drawState) {
        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(Orientation.VERTICAL) - spacerList.size() > 0 ? 1 : 0);
        System.out.println("v: " + spacerList.size() + " " + splitSize + " " + size);
        for (Spacer spacer : spacerList) {
            drawState.getVerticalSpacers().put(spacer, size.getHeight() / splitSize);
        }
        Set<Component> components = componentList.stream().filter(component -> component.spacers(Orientation.VERTICAL) > 0).collect(Collectors.toSet());
        int componentSplitSize = size.getHeight() / (splitSize == 0 ? 1 : splitSize);
        componentList.forEach(component -> {
            Size current = size.copy();
            current.setHeight(component.size().getHeight());
            if (components.contains(component)) {
                current.setHeight(current.getHeight() + (componentSplitSize / components.size()));
            }
            component.spacerSize(current, drawState);
        });
    }

    @Override
    public int spacers(Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void draw(Graphics2D g, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        Point current = new Point(point.getX(), point.getY());
        current.add(offset);
        for (Component component : componentList) {
            component.draw(g, drawState, current);
            current.setX(point.getX());
        }
        point.addX(actualSize(g, drawState).getWidth());
    }
}
