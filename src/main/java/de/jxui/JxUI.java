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

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    protected UserState userState = new UserState(size, () -> {
        if (drawState == null) return;
        drawState.setRepaint(true);
    });

    private Canvas canvas = null;
    private Runnable repainter = () -> {
        if (canvas == null) return;
        draw(canvas);
    };

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
                MouseClickEvent mouseClickEvent = new MouseClickEvent(e);
                log.debug("Click: {}", mouseClickEvent);
                component.event(userState, drawState, new Point(0, 0), mouseClickEvent);
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                }
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                MouseDragEvent mouseDragEvent = new MouseDragEvent(e);
                log.debug("Drag: {}", mouseDragEvent);
                component.event(userState, drawState, new Point(0, 0), mouseDragEvent);
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                MouseMoveEvent mouseMoveEvent = new MouseMoveEvent(e);
                log.debug("Move: {}", mouseMoveEvent);
                component.event(userState, drawState, new Point(0, 0), mouseMoveEvent);
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                }
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (drawState == null) return;
                if (mouseLocation == null) return;
                KeyTypeEvent keyTypeEvent = new KeyTypeEvent(e);
                log.debug("Keyboard: {}", keyTypeEvent);
                component.event(userState, drawState, new Point(0, 0), keyTypeEvent);
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                }
            }
        });
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    private BufferedImage bufferedImage = null;

    public void draw(Canvas canvas) {
        log.debug("Cleanup");
        component.cleanUp();

        if (bufferedImage == null || bufferedImage.getWidth() != canvas.getWidth() || bufferedImage.getHeight() != canvas.getHeight()) {
            bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        this.canvas = canvas;
        log.debug("Draw: " + canvas.getSize());
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawState = new DrawState(debug);
        Size canvasSize = new Size(canvas.getWidth(), canvas.getHeight());
        size.setWidth(canvasSize.getWidth());
        size.setHeight(canvasSize.getHeight());
        component.size(canvasSize, userState, drawState);
        log.debug("DrawState: {}   CanvasSize: {}", drawState.getSizeMap(), canvasSize);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        component.draw(graphics, userState, drawState, new Point(0, 0));
        canvas.getGraphics().drawImage(bufferedImage, 0, 0, (img, infoflags, x, y, width, height) -> false);
    }
}
