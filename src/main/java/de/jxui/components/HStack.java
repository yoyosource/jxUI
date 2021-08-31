package de.jxui.components;

import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.Spacers;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class HStack extends Stack {

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
    public void spacerSize(Size size, Spacers spacers) {
        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        for (Spacer spacer : spacerList) {
            spacers.getHorizontalSpacers().put(spacer, size.getWidth() / spacerList.size());
        }
        size.setWidth(0);
        componentList.forEach(component -> {
            component.spacerSize(size.copy(), spacers);
        });
    }

    @Override
    public void draw(Graphics2D g, Spacers spacers, Point point) {
        Point current = new Point(point.getX(), point.getY());
        for (Component component : componentList) {
            component.draw(g, spacers, current);
            current.setY(point.getY());
        }
        point.addY(size().getHeight());
    }
}
