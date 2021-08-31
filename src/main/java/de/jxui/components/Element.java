package de.jxui.components;

import de.jxui.utils.Direction;
import de.jxui.utils.Offset;
import de.jxui.utils.Padding;
import lombok.NonNull;

public abstract class Element<T> implements Component, ComponentPadding<T>, ComponentOffset<T> {

    protected Padding padding = new Padding(0, 0, 0, 0);
    protected Offset offset = new Offset();

    protected Element() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding() {
        padding = new Padding();
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T padding(@NonNull Padding padding) {
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
        offset = new Offset();
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T offset(@NonNull Offset offset) {
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
