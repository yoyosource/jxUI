package de.jxui.components;

import de.jxui.utils.RenderContext;
import de.jxui.utils.Size;

import java.util.Arrays;

public class HStack extends Stack {

    public HStack(Component... components) {
        componentList.addAll(Arrays.asList(components));
    }

    public HStack add(Component component) {
        componentList.add(component);
        return this;
    }

    @Override
    protected void mergeSize(Size current, Size calculated) {
        current.mergeWidth(calculated);
    }

    @Override
    public void populateRenderContext(RenderContext renderContext) {
        componentList.forEach(component -> {
            if (component instanceof Spacer) {
                renderContext.getHorizontalSpacer().add((Spacer) component);
            }
            component.populateRenderContext(renderContext);
        });
    }
}
