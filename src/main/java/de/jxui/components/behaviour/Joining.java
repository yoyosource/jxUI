package de.jxui.components.behaviour;

import de.jxui.components.Component;

import java.util.function.Supplier;

public interface Joining<T> {
    T Joining(Supplier<Component> component);
}
