package de.jxui.components.behaviour;

import de.jxui.utils.Direction;

public interface Offset<T> {
    T offset();
    T offset(de.jxui.utils.Offset offset);
    T offset(Direction direction, int value);
}
