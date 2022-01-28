package de.jxui.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    private int x;
    private int y;

    public Point add(Size size) {
        if (size == null) return this;
        x += size.getWidth();
        y += size.getHeight();
        return this;
    }

    public Point add(Offset offset) {
        if (offset == null) return this;
        x += offset.getLeft();
        y += offset.getTop();
        return this;
    }

    public Point add(Padding padding) {
        if (padding == null) return this;
        x += padding.getLeft();
        y += padding.getTop();
        return this;
    }

    public void addX(int dx) {
        x += dx;
    }

    public void addY(int dy) {
        y += dy;
    }

    public Point copy() {
        return new Point(x, y);
    }
}
