package de.jxui.components;

import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.Spacers;

import java.awt.*;

public interface Component {
    Size size();
    default void spacerSize(Size size, Spacers spacers) {
    }
    default void draw(Graphics2D g, Spacers spacers, Point point) {
    }
}
