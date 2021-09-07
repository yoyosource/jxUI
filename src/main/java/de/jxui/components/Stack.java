package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Orientation;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j
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
