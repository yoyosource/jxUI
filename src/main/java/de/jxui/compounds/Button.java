package de.jxui.compounds;

import de.jxui.action.ButtonAction;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.MouseClickEvent;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public class Button implements Component {

    private ButtonAction buttonAction;
    private Component component;

    public Button(ButtonAction buttonAction, Component component) {
        this.buttonAction = buttonAction;
        this.component = component;
    }

    @Override
    public Size size(UserState userState) {
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        component.size(size, userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return component.spacers(userState, orientation);
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (event instanceof MouseClickEvent mouseClickEvent) {
            Size size = drawState.getSizeMap().get(this);
            Point clickPoint = mouseClickEvent.getPoint();
            if (clickPoint.getX() >= point.getX() && clickPoint.getX() <= point.getX() + size.getWidth()) {
                if (clickPoint.getY() >= point.getY() && clickPoint.getY() <= point.getY() + size.getHeight()) {
                    if (!buttonAction.run(userState, mouseClickEvent)) {
                        component.event(userState, drawState, point, event);
                    }
                } else {
                    component.event(userState, drawState, point, event);
                }
            } else {
                component.event(userState, drawState, point, event);
            }
        } else {
            component.event(userState, drawState, point, event);
        }
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        component.draw(g, userState, drawState, point);
    }
}
