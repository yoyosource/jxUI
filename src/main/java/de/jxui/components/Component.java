package de.jxui.components;

import de.jxui.utils.RenderContext;
import de.jxui.utils.Size;

public interface Component {
    Size size();
    default void populateRenderContext(RenderContext renderContext) {

    }
}
