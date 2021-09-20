package de.jxui.components.event;

import de.jxui.behaviour.Action;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.ClickEvent;
import de.jxui.other.Consume;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public class Button implements Component {

    private Action<ClickEvent> buttonAction;
    private Component component;

    public Button(Action<ClickEvent> buttonAction, Component component) {
        this.buttonAction = buttonAction;
        this.component = component;
    }

    @Override
    public void cleanUp() {
        component.cleanUp();
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
        if (event instanceof ClickEvent clickEvent) {
            Size size = drawState.getSizeMap().get(this);
            Point clickPoint = clickEvent.getPoint();
            if (clickPoint.getX() >= point.getX() && clickPoint.getX() <= point.getX() + size.getWidth()) {
                if (clickPoint.getY() >= point.getY() && clickPoint.getY() <= point.getY() + size.getHeight()) {
                    if (!buttonAction.run(userState, clickEvent)) {
                        component.event(userState, drawState, point, event);
                    } else {
                        throw new Consume();
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
