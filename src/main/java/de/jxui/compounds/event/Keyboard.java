package de.jxui.compounds.event;

import de.jxui.action.KeyTypeAction;
import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.events.KeyTypeEvent;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public class Keyboard implements Component {

    private KeyTypeAction keyTypeAction;
    private Component component;

    public Keyboard(KeyTypeAction keyTypeAction, Component component) {
        this.keyTypeAction = keyTypeAction;
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
        if (event instanceof KeyTypeEvent keyTypeEvent) {
            if (!keyTypeAction.run(userState, keyTypeEvent)) {
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
