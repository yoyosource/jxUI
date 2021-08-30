package de.jxui.components;

import de.jxui.utils.RenderContext;
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

    @Override
    public void populateRenderContext(RenderContext renderContext) {
        componentList.forEach(component -> {
            if (component instanceof Spacer) {
                renderContext.getVerticalSpacer().add((Spacer) component);
            }
            component.populateRenderContext(renderContext);
        });
    }
}
