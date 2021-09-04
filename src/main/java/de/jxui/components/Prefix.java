package de.jxui.components;

import java.util.function.Supplier;

public interface Prefix<T> {
    T Prefix(Supplier<Component> component);
}
