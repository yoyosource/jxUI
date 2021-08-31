package de.jxui.components;

import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.State;

import java.awt.*;

public interface Component {
    Size size();
    default Size actualSize(Graphics2D g, State state) {
        return size();
    }
    default void spacerSize(Size size, State state) {
    }
    default int spacers(Orientation orientation) {
        return 0;
    }
    default void draw(Graphics2D g, State state, Point point) {
    }
}
