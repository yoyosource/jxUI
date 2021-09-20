package de.jxui.components.properties;

import de.jxui.components.Component;

import java.util.function.Supplier;

public interface Prefix<T> {
    T Prefix(Supplier<Component> component);
}
