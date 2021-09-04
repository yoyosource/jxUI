package de.jxui.components;

import java.util.function.Supplier;

public interface Joining<T> {
    T Joining(Supplier<Component> component);
}
