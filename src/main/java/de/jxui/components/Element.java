package de.jxui.components;

import de.jxui.components.properties.Offset;
import de.jxui.components.properties.Padding;
import de.jxui.utils.Direction;
import lombok.NonNull;

public abstract class Element<T> implements Component, Padding<T>, Offset<T> {

    protected de.jxui.utils.Padding padding = new de.jxui.utils.Padding(0, 0, 0, 0);
    protected de.jxui.utils.Offset offset = new de.jxui.utils.Offset();

    protected Element() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding() {
        padding = new de.jxui.utils.Padding();
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding(@NonNull de.jxui.utils.Padding padding) {
        this.padding = padding;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding(Direction direction, int value) {
        switch (direction) {
            case RIGHT -> padding.setRight(value);
            case LEFT -> padding.setLeft(value);
            case TOP -> padding.setTop(value);
            case BOTTOM -> padding.setBottom(value);
            default -> {}
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T offset() {
        offset = new de.jxui.utils.Offset();
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T offset(@NonNull de.jxui.utils.Offset offset) {
        this.offset = offset;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T offset(Direction direction, int value) {
        switch (direction) {
            case LEFT -> offset.setLeft(value);
            case TOP -> offset.setTop(value);
            default -> {}
        }
        return (T) this;
    }
}
