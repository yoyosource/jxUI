package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.components.HStack;
import de.jxui.components.Stack;
import de.jxui.components.VStack;
import de.jxui.components.properties.Joining;
import de.jxui.components.properties.Prefix;
import de.jxui.components.properties.Suffix;
import de.jxui.events.Event;
import de.jxui.utils.*;
import de.jxui.utils.Point;
import lombok.NonNull;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Render some kind of {@link Map} either vertically or horizontally.
 */
public class ComponentMap<K, V> implements Component, Prefix<ComponentMap<K, V>>, Joining<ComponentMap<K, V>>, Suffix<ComponentMap<K, V>> {

    private Function<K, Component> keyFunction;
    private Function<V, Component> valueFunction;
    private Map<K, V> map;

    private Orientation orientation = Orientation.VERTICAL;
    private Stack<?> component;

    private Supplier<Component> prefix = null;
    private Supplier<Component> joining = null;
    private Supplier<Component> suffix = null;

    private Supplier<Component> entryPrefix = null;
    private Supplier<Component> entryJoining = null;
    private Supplier<Component> entrySuffix = null;

    private Integer hashCode = null;

    public ComponentMap(@NonNull Function<K, Component> keyFunction, @NonNull Function<V, Component> valueFunction, @NonNull Map<K, V> map) {
        this.keyFunction = keyFunction;
        this.valueFunction = valueFunction;
        this.map = map;
    }

    public ComponentMap(@NonNull Orientation orientation, @NonNull Function<K, Component> keyFunction, @NonNull Function<V, Component> valueFunction, @NonNull Map<K, V> map) {
        this.orientation = orientation;
        this.keyFunction = keyFunction;
        this.valueFunction = valueFunction;
        this.map = map;
    }

    @Override
    public ComponentMap<K, V> Prefix(Supplier<Component> component) {
        this.prefix = component;
        return this;
    }

    @Override
    public ComponentMap<K, V> Joining(Supplier<Component> component) {
        this.joining = component;
        return this;
    }

    @Override
    public ComponentMap<K, V> Suffix(Supplier<Component> component) {
        this.suffix = component;
        return this;
    }

    public ComponentMap<K, V> EntryPrefix(Supplier<Component> component) {
        this.entryPrefix = component;
        return this;
    }

    public ComponentMap<K, V> EntryJoining(Supplier<Component> component) {
        this.entryJoining = component;
        return this;
    }

    public ComponentMap<K, V> EntrySuffix(Supplier<Component> component) {
        this.entrySuffix = component;
        return this;
    }

    @Override
    public void cleanUp() {
        if (component != null) {
            component.cleanUp();
        }
        if (hashCode == null || hashCode != map.hashCode()) {
            component = null;
        }
    }

    @Override
    public Size size(UserState userState) {
        if (component == null) {
            hashCode = map.hashCode();
            if (orientation == Orientation.VERTICAL) {
                component = new VStack();
            } else {
                component = new HStack();
            }
            if (prefix != null) {
                component.add(prefix.get());
            }
            boolean b = false;
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (b && joining != null) {
                    component.add(joining.get());
                }
                b = true;
                Stack<?> inner;
                if (orientation == Orientation.VERTICAL) {
                    inner = new HStack();
                } else {
                    inner = new VStack();
                }
                if (entryPrefix != null) {
                    inner.add(entryPrefix.get());
                }
                inner.add(keyFunction.apply(entry.getKey()));
                if (entryJoining != null) {
                    inner.add(entryJoining.get());
                }
                inner.add(valueFunction.apply(entry.getValue()));
                if (entrySuffix != null) {
                    inner.add(entrySuffix.get());
                }
                component.add(inner);
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
