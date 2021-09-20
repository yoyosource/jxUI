package de.jxui;

import de.jxui.components.Component;
import de.jxui.events.*;
import de.jxui.other.Consume;
import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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

    @Getter
    private UserState userState;

    private Canvas canvas = null;
    protected Runnable repainter = () -> {
        if (canvas == null) return;
        draw(canvas);
    };

    protected JxUI() {
        setUserState(new UserState());
    }

    public JxUI(@NonNull Component component) {
        this.component = component;
        setUserState(new UserState());
    }

    public JxUI setUserState(@NonNull UserState userState) {
        userState.setSize(size);
        userState.setChangeRunnable(() -> {
            if (drawState == null) return;
            drawState.setRepaint(true);
        });
        this.userState = userState;
        return this;
    }

    public void addListenersTo(Canvas canvas) {
        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                ScrollEvent scrollEvent = new ScrollEvent(e);
                log.debug("Wheel: {}", scrollEvent);
                try {
                    component.event(userState, drawState, new Point(0, 0), scrollEvent);
                } catch (Consume consume) {
                    // Ignored
                }
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                    canvas.repaint();
                }
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                ClickEvent clickEvent = new ClickEvent(e);
                log.debug("Click: {}", clickEvent);
                try {
                    component.event(userState, drawState, new Point(0, 0), clickEvent);
                } catch (Consume consume) {
                    // Ignored
                }
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                    canvas.repaint();
                }
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                DragEvent dragEvent = new DragEvent(e);
                log.debug("Drag: {}", dragEvent);
                try {
                    component.event(userState, drawState, new Point(0, 0), dragEvent);
                } catch (Consume consume) {
                    // Ignored
                }
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                    canvas.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLocation = new Point(e.getX(), e.getY());
                if (drawState == null) return;
                MoveEvent moveEvent = new MoveEvent(e);
                log.debug("Move: {}", moveEvent);
                try {
                    component.event(userState, drawState, new Point(0, 0), moveEvent);
                } catch (Consume consume) {
                    // Ignored
                }
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                    canvas.repaint();
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
                try {
                    component.event(userState, drawState, new Point(0, 0), keyTypeEvent);
                } catch (Consume consume) {
                    // Ignored
                }
                log.debug("UserState: {}", userState);
                if (drawState.isRepaint()) {
                    repainter.run();
                    canvas.repaint();
                }
            }
        });
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public void draw(Canvas canvas) {
        log.debug("Cleanup");
        component.cleanUp();

        BufferedImage bufferedImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
        canvas.getGraphics().drawImage(bufferedImage, 0, 0, (img, infoflags, x, y, width, height) -> true);
    }
}
