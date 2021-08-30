package de.jxui.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Size {
    private int width;
    private int height;

    public Size add(Size size) {
        width += size.width;
        height += size.height;
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
}
