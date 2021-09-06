package de.jxui.components.behaviour;

import de.jxui.utils.Direction;
import de.jxui.utils.Padding;

public interface Padding<T> {
    T padding();
    T padding(de.jxui.utils.Padding padding);
    T padding(Direction direction, int value);
}
