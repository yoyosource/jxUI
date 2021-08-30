package de.jxui.components;

import de.jxui.utils.Size;

import java.util.Arrays;

public class VStack extends Stack {

    public VStack(Component... components) {
        componentList.addAll(Arrays.asList(components));
    }

    public VStack add(Component component) {
        componentList.add(component);
        return this;
    }

    @Override
    protected void mergeSize(Size current, Size calculated) {
        current.mergeHeight(calculated);
    }
}
