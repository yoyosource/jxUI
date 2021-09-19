package de.jxui.components.event;

import de.jxui.action.Action;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.MouseMoveEvent;
import de.jxui.other.Consume;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public class Move implements Component {

    private Action<MouseMoveEvent> moveAction;
    private Component component;

    public Move(Action<MouseMoveEvent> moveAction, Component component) {
        this.moveAction = moveAction;
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
        if (event instanceof MouseMoveEvent mouseMoveEvent) {
            Size size = drawState.getSizeMap().get(this);
            Point clickPoint = mouseMoveEvent.getPoint();
            if (clickPoint.getX() >= point.getX() && clickPoint.getX() <= point.getX() + size.getWidth()) {
                if (clickPoint.getY() >= point.getY() && clickPoint.getY() <= point.getY() + size.getHeight()) {
                    if (!moveAction.run(userState, mouseMoveEvent)) {
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
        component.draw(g, userState, drawState, point);
    }
}
