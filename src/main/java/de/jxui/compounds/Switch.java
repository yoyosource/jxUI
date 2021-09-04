package de.jxui.compounds;

import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Switch implements Component {

    private List<Case> caseList = new ArrayList<>();
    private Component defaultComponent = null;

    @Data
    @AllArgsConstructor
    private static class Case {
        private Predicate<UserState> predicate;
        private Component component;
    }

    public Switch Case(Predicate<UserState> predicate, @NonNull Component component) {
        caseList.add(new Case(predicate, component));
        return this;
    }

    public Switch Default(Component component) {
        this.defaultComponent = component;
        return this;
    }

    @Override
    public void cleanUp() {
        caseList.forEach(current -> {
            current.component.cleanUp();
        });
        if (defaultComponent != null) {
            defaultComponent.cleanUp();
        }
    }

    @Override
    public Size size(UserState userState) {
        for (Case current : caseList) {
            if (current.predicate.test(userState)) {
                return current.component.size(userState);
            }
        }
        if (defaultComponent != null) {
            return defaultComponent.size(userState);
        } else {
            return new Size(0, 0);
        }
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        for (Case current : caseList) {
            if (current.predicate.test(userState)) {
                current.component.size(size, userState, drawState);
                drawState.getSizeMap().put(this, drawState.getSizeMap().get(current.component));
                return;
            }
        }
        if (defaultComponent != null) {
            defaultComponent.size(size, userState, drawState);
            drawState.getSizeMap().put(this, drawState.getSizeMap().get(defaultComponent));
        }
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        for (Case current : caseList) {
            if (current.predicate.test(userState)) {
                return current.component.spacers(userState, orientation);
            }
        }
        if (defaultComponent != null) {
            return defaultComponent.spacers(userState, orientation);
        } else {
            return 0;
        }
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        for (Case current : caseList) {
            if (current.predicate.test(userState)) {
                current.component.event(userState, drawState, point, event);
                return;
            }
        }
        if (defaultComponent != null) {
            defaultComponent.event(userState, drawState, point, event);
        }
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        for (Case current : caseList) {
            if (current.predicate.test(userState)) {
                current.component.draw(g, userState, drawState, point);
                return;
            }
        }
        if (defaultComponent != null) {
            defaultComponent.draw(g, userState, drawState, point);
        }
    }
}
