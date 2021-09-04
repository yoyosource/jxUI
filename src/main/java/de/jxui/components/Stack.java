package de.jxui.components;

import de.jxui.utils.Size;
import de.jxui.utils.UserState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Stack<T> extends Element<T> {

    private BiConsumer<Size, Size> mergeSize;
    protected List<Component> componentList = new ArrayList<>();

    protected Stack(BiConsumer<Size, Size> mergeSize) {
        this.mergeSize = mergeSize;
    }

    public abstract T add(Component component);

    @Override
    public void cleanUp() {
        componentList.forEach(Component::cleanUp);
    }

    @Override
    public Size size(UserState userState) {
        Size size = new Size(0, 0);
        componentList.forEach(component -> {
            mergeSize.accept(size, component.size(userState));
        });
        return size.add(padding);
    }
}
