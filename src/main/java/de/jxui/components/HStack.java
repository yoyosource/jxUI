package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HStack extends Stack<HStack> {

    public HStack(Component... components) {
        super(Size::mergeWidth);
        for (Component component : components) {
            add(component);
        }
    }

    public HStack add(Component component) {
        if (component instanceof Spacer) {
            ((Spacer) component).orientation = Orientation.HORIZONTAL;
        }
        componentList.add(component);
        return this;
    }

    @Override
    public void spacerSize(Size size, DrawState drawState) {
        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(Orientation.HORIZONTAL) - spacerList.size() > 0 ? 1 : 0);
        for (Spacer spacer : spacerList) {
            drawState.getHorizontalSpacers().put(spacer, size.getWidth() / splitSize);
        }
        Set<Component> components = componentList.stream().filter(component -> component.spacers(Orientation.HORIZONTAL) > 0).collect(Collectors.toSet());
        int componentSplitSize = size.getWidth() / (splitSize == 0 ? 1 : splitSize);
        componentList.forEach(component -> {
            Size current = size.copy();
            current.setWidth(component.size().getWidth());
            if (components.contains(component)) {
                current.setWidth(current.getWidth() + (componentSplitSize / components.size()));
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
        Point current = new Point(point.getX(), point.getY());
        current.add(offset);
        for (Component component : componentList) {
            component.draw(g, drawState, current);
            current.setY(point.getY());
        }
        point.addY(actualSize(g, drawState).getHeight());
    }
}
