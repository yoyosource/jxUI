package de.jxui;

import de.jxui.components.Component;
import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class JxUIDraw {

    public BufferedImage draw(Component component, UserState userState, int width, int height, Consumer<DrawState> drawStateConsumer, Size size, boolean debug) {
        log.debug("Cleanup");
        component.cleanUp();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        log.debug("Draw: " + width + " " + height);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        DrawState drawState = new DrawState(debug);
        drawStateConsumer.accept(drawState);
        Size canvasSize = new Size(width, height);
        size.setWidth(canvasSize.getWidth());
        size.setHeight(canvasSize.getHeight());
        component.size(canvasSize, userState, drawState);
        log.debug("DrawState: {}   CanvasSize: {}", drawState.getSizeMap(), canvasSize);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        component.draw(graphics, userState, drawState, new Point(0, 0));
        return bufferedImage;
    }
}
