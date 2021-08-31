package de.jxui.components;

import de.jxui.utils.Direction;
import de.jxui.utils.Padding;

public interface ComponentPadding<T> {
    T padding();
    T padding(Padding padding);
    T padding(Direction direction, int value);
}
