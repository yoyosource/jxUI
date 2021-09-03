package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.events.MouseClickEvent;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.function.BiPredicate;

public class Button implements Component {

    private BiPredicate<UserState, MouseClickEvent> mouseEvent;
    private Component component;

    public Button(BiPredicate<UserState, MouseClickEvent> mouseEvent, Component component) {
        this.mouseEvent = mouseEvent;
        this.component = component;
    }

    @Override
    public Size size(UserState userState) {
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        component.size(size, userState, drawState);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return component.spacers(userState, orientation);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        component.draw(g, userState, drawState, point);
    }
}
