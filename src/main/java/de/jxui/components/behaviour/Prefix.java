package de.jxui.components.behaviour;

import de.jxui.components.Component;

import java.util.function.Supplier;

public interface Prefix<T> {
    T Prefix(Supplier<Component> component);
}
