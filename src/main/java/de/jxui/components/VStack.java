package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VStack extends Stack implements ComponentPadding<VStack> {

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

    public VStack padding() {
        padding = new Padding();
        return this;
    }

    public VStack padding(Padding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public void spacerSize(Size size, State state) {
        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(Orientation.VERTICAL) > 0 ? 1 : 0);
        for (Spacer spacer : spacerList) {
            state.getVerticalSpacers().put(spacer, size.getHeight() / splitSize);
        }
        Set<Component> components = componentList.stream().filter(component -> component.spacers(Orientation.VERTICAL) > 0).collect(Collectors.toSet());
        int componentSplitSize = size.getHeight() / (splitSize == 0 ? 1 : splitSize);
        componentList.forEach(component -> {
            Size current = size.copy();
            current.setHeight(component.size().getHeight());
            if (components.contains(component)) {
                current.setHeight(current.getHeight() + (componentSplitSize / components.size()));
            }
            component.spacerSize(current, state);
        });
    }

    @Override
    public int spacers(Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        Point current = new Point(point.getX(), point.getY());
        for (Component component : componentList) {
            component.draw(g, state, current);
            current.setX(point.getX());
        }
        point.addX(actualSize(g, state).getWidth());
    }
}
