package de.jxui.components;

import de.jxui.utils.Orientation;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.DrawState;

import java.awt.*;

public interface Component {
    Size size();
    default Size actualSize(Graphics2D g, DrawState drawState) {
        return size();
    }
    default void spacerSize(Size size, DrawState drawState) {
    }
    default int spacers(Orientation orientation) {
        return 0;
    }
    default void draw(Graphics2D g, DrawState drawState, Point point) {
    }
}
