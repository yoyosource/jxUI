package de.jxui.utils;

import de.jxui.components.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.IdentityHashMap;
import java.util.Map;

@Getter
@ToString
public class DrawState {
    private boolean debug = false;

    @Setter
    private boolean repaint = false;

    private Map<Component, Size> sizeMap = new IdentityHashMap<>();

    public DrawState(boolean debug) {
        this.debug = debug;
    }
}
