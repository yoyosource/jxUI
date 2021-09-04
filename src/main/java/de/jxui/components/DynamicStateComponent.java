package de.jxui.components;

import de.jxui.utils.*;
import de.jxui.utils.Point;

import java.awt.*;
import java.util.function.Function;

public class DynamicStateComponent implements Component{

    private Function<UserState, Component> componentFunction;
    private Component component = null;

    public DynamicStateComponent(Function<UserState, Component> componentFunction) {
        this.componentFunction = componentFunction;
    }

    @Override
    public void cleanUp() {
        component.cleanUp();
        component = null;
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            component = componentFunction.apply(userState);
        }
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (component == null) {
            size(userState);
        }
        component.size(size, userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        if (component == null) {
            size(userState);
        }
        return component.spacers(userState, orientation);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component != null) {
            component.draw(g, userState, drawState, point);
        }
    }
}
