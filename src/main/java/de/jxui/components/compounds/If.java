package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.Predicate;

public class If implements Component {

    private Predicate<UserState> predicate;
    private Component trueComponent;
    private Component falseComponent;

    public If(Predicate<UserState> predicate, @NonNull Component component) {
        this.predicate = predicate;
        this.trueComponent = component;
    }

    public If Else(Component component) {
        this.falseComponent = component;
        return this;
    }

    @Override
    public void cleanUp() {
        trueComponent.cleanUp();
        if (falseComponent != null) {
            falseComponent.cleanUp();
        }
    }

    @Override
    public Size size(UserState userState) {
        if (predicate.test(userState)) {
            return trueComponent.size(userState);
        } else if (falseComponent != null) {
            return falseComponent.size(userState);
        } else {
            return new Size(0, 0);
        }
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (predicate.test(userState)) {
            trueComponent.size(size, userState, drawState);
            drawState.getSizeMap().put(this, drawState.getSizeMap().get(trueComponent));
        } else if (falseComponent != null) {
            falseComponent.size(size, userState, drawState);
            drawState.getSizeMap().put(this, drawState.getSizeMap().get(falseComponent));
        }
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        if (predicate.test(userState)) {
            return trueComponent.spacers(userState, orientation);
        } else if (falseComponent != null) {
            return falseComponent.spacers(userState, orientation);
        } else {
            return 0;
        }
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (predicate.test(userState)) {
            trueComponent.event(userState, drawState, point, event);
        } else if (falseComponent != null) {
            falseComponent.event(userState, drawState, point, event);
        }
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (predicate.test(userState)) {
            trueComponent.draw(g, userState, drawState, point);
        } else if (falseComponent != null) {
            falseComponent.draw(g, userState, drawState, point);
        }
    }
}
