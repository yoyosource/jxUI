package de.jxui.components.properties;

import de.jxui.components.Component;

import java.util.function.Supplier;

public interface Suffix<T> {
    T Suffix(Supplier<Component> component);
}
