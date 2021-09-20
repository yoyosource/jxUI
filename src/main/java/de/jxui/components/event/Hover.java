package de.jxui.components.event;

import de.jxui.action.Action;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.MoveEvent;
import de.jxui.other.Consume;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public class Hover implements Component {

    private Action<MoveEvent> enterAction;
    private Action<MoveEvent> exitAction;
    private boolean hovering = false;
    private Component component;

    public Hover(Action<MoveEvent> enterAction, Action<MoveEvent> exitAction, Component component) {
        this.enterAction = enterAction;
        this.exitAction = exitAction;
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
        if (event instanceof MoveEvent moveEvent) {
            Size size = drawState.getSizeMap().get(this);
            Point clickPoint = moveEvent.getPoint();
            if (clickPoint.getX() >= point.getX() && clickPoint.getX() <= point.getX() + size.getWidth()) {
                if (clickPoint.getY() >= point.getY() && clickPoint.getY() <= point.getY() + size.getHeight()) {
                    if (hovering) {
                        return;
                    }
                    if (enterAction.run(userState, moveEvent)) {
                        hovering = true;
                        throw new Consume();
                    }
                } else {
                    if (hovering) {
                        if (exitAction.run(userState, moveEvent)) {
                            hovering = false;
                            throw new Consume();
                        }
                    }
                    component.event(userState, drawState, point, event);
                }
            } else {
                if (hovering) {
                    if (exitAction.run(userState, moveEvent)) {
                        hovering = false;
                        throw new Consume();
                    }
                }
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
