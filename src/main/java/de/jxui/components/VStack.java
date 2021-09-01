package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
    public void size(Size size, UserState userState, DrawState drawState) {
        Size currentSize = size(userState);
        Size spacerSize = size.copy().substract(currentSize);

        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(userState, Orientation.VERTICAL) - spacerList.size() > 0 ? 1 : 0);

        log.debug("Spacer: {}   Splitting: {}   Size: {}   AllowedSize: {}", spacerList.size(), splitSize, currentSize, size);
        for (Spacer spacer : spacerList) {
            spacer.size(size.copy().setHeight(spacerSize.getHeight() / splitSize), userState, drawState);
        }

        Set<Component> components = componentList.stream().filter(component -> component.spacers(userState, Orientation.VERTICAL) > 0).collect(Collectors.toSet());
        int componentSplitSize = size.getHeight() / (splitSize == 0 ? 1 : splitSize);
        componentList.forEach(component -> {
            if (drawState.getSizeMap().containsKey(component)) {
                return;
            }
            Size current = component.size(userState);
            current.setWidth(size.getWidth());
            if (components.contains(component)) {
                current.setHeight(componentSplitSize);
            }
            component.size(current, userState, drawState);
        });
        drawState.getSizeMap().put(this, size);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return componentList.stream().map(component -> component.spacers(userState, orientation)).mapToInt(value -> value).sum();
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        Point current = new Point(point.getX(), point.getY());
        current.add(offset);
        for (Component component : componentList) {
            component.draw(g, userState, drawState, current);
            current.setX(point.getX());
        }
        point.addX(drawState.getSizeMap().get(this).getWidth());
    }
}
