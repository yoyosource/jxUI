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

    public void sizeImpl(Size size, UserState userState, DrawState drawState, Orientation orientation) {
        Size currentSize = size(userState);
        Size spacerSize = size.copy().substract(currentSize);

        List<Spacer> spacerList = componentList.stream().filter(Spacer.class::isInstance).map(Spacer.class::cast).filter(spacer -> spacer.getSize() == -1).collect(Collectors.toList());
        int splitSize = spacerList.size() + (spacers(userState, orientation) - spacerList.size() > 0 ? 1 : 0);

        log.debug("Spacer: {}   Splitting: {}   Size: {}   AllowedSize: {}", spacerList.size(), splitSize, currentSize, size);
        for (Spacer spacer : spacerList) {
            Size current = size.copy();
            if (orientation == Orientation.VERTICAL) {
                current.setHeight(spacerSize.getHeight() / splitSize);
            } else {
                current.setWidth(spacerSize.getWidth() / splitSize);
            }
            spacer.size(currentSize, userState, drawState);
        }

        Set<Component> components = componentList.stream().filter(component -> !drawState.getSizeMap().containsKey(component)).filter(component -> component.spacers(userState, orientation) > 0).collect(Collectors.toSet());
        int componentSplitSize = (orientation == Orientation.VERTICAL ? spacerSize.getHeight() : spacerSize.getWidth()) / (splitSize == 0 ? 1 : splitSize);
        log.debug("Other Component sizes: {}   Components: {}", componentSplitSize, components.size());
        componentList.forEach(component -> {
            if (drawState.getSizeMap().containsKey(component)) {
                return;
            }
            Size current = component.size(userState);
            if (orientation == Orientation.VERTICAL) {
                current.setWidth(size.getWidth());
                if (components.contains(component)) {
                    current.setHeight(current.getHeight() + componentSplitSize / components.size());
                }
            } else {
                current.setHeight(size.getHeight());
                if (components.contains(component)) {
                    current.setWidth(current.getWidth() + componentSplitSize / components.size());
                }
            }
            component.size(current, userState, drawState);
        });
        drawState.getSizeMap().put(this, size);
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
