package de.jxui.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Size {
    private int width;
    private int height;

    public Size add(Size size) {
        width += size.width;
        height += size.height;
        return this;
    }

    public Size add(Padding padding) {
        width += padding.getLeft();
        width += padding.getRight();
        height += padding.getBottom();
        height += padding.getTop();
        return this;
    }

    public Size substract(Size size) {
        width -= size.width;
        height -= size.height;
        return this;
    }

    public Size mergeHeight(Size size) {
        width = Math.max(width, size.width);
        height += size.height;
        return this;
    }

    public Size mergeWidth(Size size) {
        width += size.width;
        height = Math.max(height, size.height);
        return this;
    }

    public Size merge(Size size) {
        width = Math.max(width, size.width);
        height = Math.max(height, size.height);
        return this;
    }

    public Size copy() {
        return new Size(width, height);
    }
}
