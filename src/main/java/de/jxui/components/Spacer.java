package de.jxui.components;

import de.jxui.utils.Size;

public class Spacer implements Component {

    private int size;

    public Spacer() {
        this.size = -1;
    }

    public Spacer(int size) {
        if (size < 0) size = 0;
        this.size = size;
    }

    @Override
    public Size size() {
        if (size == -1) {
            return new Size(0, 0);
        }
        return new Size(size, size);
    }
}
