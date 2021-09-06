package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class StateComponent<T extends Component> implements Component {

    private Supplier<T> componentSupplier;
    private T component;
    private BiConsumer<UserState, T> stateChange;

    public StateComponent(Supplier<T> componentSupplier) {
        this.componentSupplier = componentSupplier;
    }

    public StateComponent(@NonNull T component, @NonNull BiConsumer<UserState, T> stateChange) {
        this.component = component;
        this.stateChange = stateChange;
    }

    @Override
    public void cleanUp() {
        if (component != null) {
            component.cleanUp();
        }
        if (componentSupplier != null) {
            component = null;
        }
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            component = componentSupplier.get();
        }
        if (stateChange != null) {
            stateChange.accept(userState, component);
        }
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        if (component == null) {
            size(userState);
        }
        component.size(size, userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        if (component == null) {
            size(userState);
        }
        return component.spacers(userState, orientation);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component != null) {
            component.draw(g, userState, drawState, point);
        }
    }
}
