package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;

public interface Component {
    default void cleanUp() {
    }
    Size size(UserState userState);
    default void size(Size size, UserState userState, DrawState drawState) {
        Size thisSize = size(userState);
        drawState.getSizeMap().put(this, thisSize);
        size.substract(thisSize);
    }
    default int spacers(UserState userState, Orientation orientation) {
        return 0;
    }
    default void event(UserState userState, DrawState drawState, Point point, Event event) {
        point.add(drawState.getSizeMap().get(this));
    }
    default void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        point.add(drawState.getSizeMap().get(this));
    }

    default void debugDraw(Graphics2D g, DrawState drawState, Point point) {
        DebugDraw.drawDebug(this, g, drawState, point);
    }
}
