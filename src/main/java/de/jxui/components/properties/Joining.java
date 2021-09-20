package de.jxui.components.properties;

import de.jxui.components.Component;

import java.util.function.Supplier;

public interface Joining<T> {
    T Joining(Supplier<Component> component);
}
