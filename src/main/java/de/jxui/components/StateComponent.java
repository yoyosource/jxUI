package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.function.BiConsumer;

public class StateComponent<T extends Component> implements Component {

    private T component;
    private BiConsumer<UserState, T> stateChange;

    public StateComponent(T component, BiConsumer<UserState, T> stateChange) {
        this.component = component;
        this.stateChange = stateChange;
    }

    @Override
    public void cleanUp() {
        component.cleanUp();
    }

    @Override
    public Size size(UserState userState) {
        stateChange.accept(userState, component);
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
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        component.draw(g, userState, drawState, point);
    }
}
