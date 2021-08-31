package de.jxui.components;

import de.jxui.utils.Padding;
import de.jxui.utils.Size;
import de.jxui.utils.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Stack implements Component {

    private BiConsumer<Size, Size> mergeSize;
    protected List<Component> componentList = new ArrayList<>();
    protected Padding padding = null;

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
    public Size actualSize(Graphics2D g, State state) {
        Size size = new Size(0, 0);
        componentList.forEach(component -> {
            mergeSize.accept(size, component.actualSize(g, state));
        });
        return size.add(padding);
    }
}
