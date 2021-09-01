package de.jxui;

import de.jxui.components.Component;
import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class JxUI {

    @Setter
    @Getter
    private boolean debug = false;

    protected Component component;

    private Size size = new Size(0, 0);

    @Setter
    @Getter
    @Accessors(chain = true)
    @NonNull
    protected UserState userState = new UserState(size);

    protected JxUI() {

    }

    public JxUI(@NonNull Component component) {
        this.component = component;
    }

    public void draw(Canvas canvas) {
        BufferedImage bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        DrawState drawState = new DrawState(debug);
        Size canvasSize = new Size(canvas.getWidth(), canvas.getHeight());
        size.setWidth(canvasSize.getWidth());
        size.setHeight(canvasSize.getHeight());
        component.size(canvasSize, userState, drawState);
        System.out.println(drawState.getSizeMap());

        log.debug(canvasSize + " " + drawState);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        component.draw(graphics, userState, drawState, new Point(0, 0));
        canvas.getGraphics().drawImage(bufferedImage, 0, 0, (img, infoflags, x, y, width, height) -> true);
    }
}
