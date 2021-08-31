package de.jxui.components;

import de.jxui.utils.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Stack<T> extends Element<T> {

    private BiConsumer<Size, Size> mergeSize;
    protected List<Component> componentList = new ArrayList<>();

    protected Stack(BiConsumer<Size, Size> mergeSize) {
        this.mergeSize = mergeSize;
    }

    @Override
    public Size size() {
        Size size = new Size(0, 0);
        componentList.forEach(component -> {
            mergeSize.accept(size, component.size());
        });
        return size.add(padding);
    }

    @Override
    public Size actualSize(Graphics2D g, DrawState drawState) {
        Size size = new Size(0, 0);
        componentList.forEach(component -> {
            mergeSize.accept(size, component.actualSize(g, drawState));
        });
        return size.add(padding);
    }
}
