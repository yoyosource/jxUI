package de.jxui;

import de.jxui.components.Component;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.State;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class JxUI {

    protected Component component;

    protected JxUI() {

    }

    public JxUI(@NonNull Component component) {
        this.component = component;
    }

    public void draw(Canvas canvas) {
        BufferedImage bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Size size = component.size();
        Size canvasSize = new Size(canvas.getWidth(), canvas.getHeight());

        Size spacerSize = canvasSize.copy().substract(size);
        if (spacerSize.getWidth() < 0) spacerSize.setWidth(0);
        if (spacerSize.getHeight() < 0) spacerSize.setHeight(0);
        State state = new State();
        component.spacerSize(spacerSize, state);

        log.debug(size + " " + canvasSize + " " + state);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        component.draw(graphics, state, new Point(0, 0));

        canvas.getGraphics().drawImage(bufferedImage, 0, 0, (img, infoflags, x, y, width, height) -> true);
    }
}
