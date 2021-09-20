package de.jxui.components.properties;

import de.jxui.utils.Direction;

public interface Padding<T> {
    T padding();
    T padding(de.jxui.utils.Padding padding);
    T padding(Direction direction, int value);
}
