package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.components.HStack;
import de.jxui.components.Stack;
import de.jxui.components.VStack;
import de.jxui.components.properties.Joining;
import de.jxui.components.properties.Prefix;
import de.jxui.components.properties.Suffix;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;

import java.awt.*;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Render some kind of {@link Collection} either vertically or horizontally.
 */
public class ComponentCollection<T> implements Component, Prefix<ComponentCollection<T>>, Joining<ComponentCollection<T>>, Suffix<ComponentCollection<T>> {

    private Function<T, Component> componentFunction;
    private Collection<T> list;

    private Orientation orientation = Orientation.VERTICAL;
    private Stack<?> component;

    private Supplier<Component> prefix = null;
    private Supplier<Component> joining = null;
    private Supplier<Component> suffix = null;

    private Integer hashCode = null;

    public ComponentCollection(@NonNull Function<T, Component> componentFunction, @NonNull Collection<T> list) {
        this.componentFunction = componentFunction;
        this.list = list;
    }

    public ComponentCollection(@NonNull Orientation orientation, @NonNull Function<T, Component> componentFunction, @NonNull Collection<T> list) {
        this.orientation = orientation;
        this.componentFunction = componentFunction;
        this.list = list;
    }

    @Override
    public ComponentCollection<T> Prefix(Supplier<Component> component) {
        this.prefix = component;
        return this;
    }

    @Override
    public ComponentCollection<T> Joining(Supplier<Component> component) {
        this.joining = component;
        return this;
    }

    @Override
    public ComponentCollection<T> Suffix(Supplier<Component> component) {
        this.suffix = component;
        return this;
    }

    @Override
    public void cleanUp() {
        if (component != null) {
            component.cleanUp();
        }
        if (hashCode == null || hashCode != list.hashCode()) {
            component = null;
        }
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            hashCode = list.hashCode();
            if (orientation == Orientation.VERTICAL) {
                component = new VStack();
            } else {
                component = new HStack();
            }
            if (prefix != null) {
                component.add(prefix.get());
            }
            boolean b = false;
            for (T current : list) {
                if (b && joining != null) {
                    component.add(joining.get());
                }
                b = true;
                component.add(componentFunction.apply(current));
            }
            if (suffix != null) {
                component.add(suffix.get());
            }
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
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (component == null) {
            size(userState);
        }
        component.event(userState, drawState, point, event);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (component != null) {
            component.draw(g, userState, drawState, point);
        }
    }
}
