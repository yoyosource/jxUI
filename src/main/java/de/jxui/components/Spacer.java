package de.jxui.components;

import de.jxui.utils.Size;

public class Spacer implements Component {

    private int size;

    public Spacer() {
        this.size = -1;
    }

    public Spacer(int size) {
        this.size = size;
    }

    @Override
    public Size size() {
        return new Size(size, size);
    }
}
