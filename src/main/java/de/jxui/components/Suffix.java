package de.jxui.components;

import java.util.function.Supplier;

public interface Suffix<T> {
    T Suffix(Supplier<Component> component);
}
