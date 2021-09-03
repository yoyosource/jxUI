package de.jxui;

import de.jxui.components.Component;
import de.jxui.events.KeyTypeEvent;
import de.jxui.events.MouseClickEvent;
import de.jxui.events.MouseDragEvent;
import de.jxui.events.MouseMoveEvent;
import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

@Slf4j
public class JxUI {

    @Setter
    @Getter
    private boolean debug = false;

    protected Component component;

    private DrawState drawState = null;
    private Point mouseLocation = null;
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

    public void addListenersTo(Canvas canvas) {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                log.debug("Click: {}", e);
                component.event(userState, drawState, new Point(0, 0), new MouseClickEvent(e));
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            long lastMouseDragged = 0;
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (System.currentTimeMillis() - lastMouseDragged < 10) return;
                if (drawState == null) return;
                lastMouseDragged = System.currentTimeMillis();
                log.debug("Drag: {}", e);
                component.event(userState, drawState, new Point(0, 0), new MouseDragEvent(e));
            }

            long lastMouseMoved = 0;
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (System.currentTimeMillis() - lastMouseMoved < 10) return;
                if (drawState == null) return;
                lastMouseMoved = System.currentTimeMillis();
                log.debug("Move: {}", e);
                component.event(userState, drawState, new Point(0, 0), new MouseMoveEvent(e));
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (drawState == null) return;
                if (mouseLocation == null) return;
                log.debug("Keyboard: {}", e);
                component.event(userState, drawState, new Point(0, 0), new KeyTypeEvent(e));
            }
        });
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public void draw(Canvas canvas) {
        log.debug("Draw: " + canvas.getSize());
        BufferedImage bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawState = new DrawState(debug);
        Size canvasSize = new Size(canvas.getWidth(), canvas.getHeight());
        size.setWidth(canvasSize.getWidth());
        size.setHeight(canvasSize.getHeight());
        component.size(canvasSize, userState, drawState);
        log.debug("DrawState: {}", drawState.getSizeMap());

        log.debug(canvasSize + " " + drawState);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        component.draw(graphics, userState, drawState, new Point(0, 0));
        canvas.getGraphics().drawImage(bufferedImage, 0, 0, (img, infoflags, x, y, width, height) -> true);
    }
}
