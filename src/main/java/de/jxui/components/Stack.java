package de.jxui.components;

import de.jxui.utils.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Stack implements Component {

    protected List<Component> componentList = new ArrayList<>();

    protected Stack(Component... components) {
        componentList.addAll(Arrays.asList(components));
    }

    protected abstract void mergeSize(Size current, Size calculated);

    @Override
    public Size size() {
        Size size = new Size(0, 0);
        componentList.forEach(component -> {
            mergeSize(size, component.size());
        });
        return size;
    }
}
