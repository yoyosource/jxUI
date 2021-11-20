package de.jxui.events;

public interface Event<T extends Event> {
    T copy();
}
