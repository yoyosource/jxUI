package de.jxui.components;

import de.jxui.utils.Size;

import java.util.Arrays;

public class ZStack extends Stack {

    public ZStack(Component... components) {
        for (Component component : components) {
            if (component instanceof Spacer) {
                throw new SecurityException();
            }
        }
        componentList.addAll(Arrays.asList(components));
    }

    public ZStack add(Component component) {
        if (component instanceof Spacer) {
            throw new SecurityException();
        }
        componentList.add(component);
        return this;
    }

    @Override
    protected void mergeSize(Size current, Size calculated) {
        current.merge(calculated);
    }
}
