package de.jxui.components;

import de.jxui.utils.Direction;
import de.jxui.utils.Offset;

public interface ComponentOffset<T> {
    T offset();
    T offset(Offset offset);
    T offset(Direction direction, int value);
}
