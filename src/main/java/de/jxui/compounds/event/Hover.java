package de.jxui.compounds.event;

import de.jxui.action.MoveAction;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.MouseMoveEvent;
import de.jxui.utils.*;
import de.jxui.utils.Point;

import java.awt.*;

public class Hover implements Component {

    private MoveAction enterAction;
    private MoveAction exitAction;
    private boolean hovering = false;
    private Component component;

    public Hover(MoveAction enterAction, MoveAction exitAction, Component component) {
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
        if (event instanceof MouseMoveEvent mouseMoveEvent) {
            Size size = drawState.getSizeMap().get(this);
            Point clickPoint = mouseMoveEvent.getPoint();
            if (clickPoint.getX() >= point.getX() && clickPoint.getX() <= point.getX() + size.getWidth()) {
                if (clickPoint.getY() >= point.getY() && clickPoint.getY() <= point.getY() + size.getHeight()) {
                    if (hovering) {
                        return;
                    }
                    enterAction.run(userState, mouseMoveEvent);
                    hovering = true;
                } else {
                    if (hovering) {
                        exitAction.run(userState, mouseMoveEvent);
                        hovering = false;
                    }
                    component.event(userState, drawState, point, event);
                }
            } else {
                if (hovering) {
                    exitAction.run(userState, mouseMoveEvent);
                    hovering = false;
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
