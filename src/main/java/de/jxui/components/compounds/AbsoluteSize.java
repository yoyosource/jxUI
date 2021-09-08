package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.awt.image.BufferedImage;

@Accessors(chain = true)
public class AbsoluteSize implements Component {

    @Setter
    private int width;

    @Setter
    private int height;

    private Component component;

    public AbsoluteSize(int width, int height, @NonNull Component component) {
        this.width = width;
        this.height = height;
        this.component = component;
    }

    @Override
    public void cleanUp() {
        component.cleanUp();
    }

    @Override
    public Size size(UserState userState) {
        return new Size(width, height);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        component.size(size(userState), userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return component.spacers(userState, orientation);
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        component.event(userState, drawState, point, event);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        debugDraw(g, drawState, point);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        component.draw(graphics, userState, drawState, new Point(0, 0));
        g.drawImage(bufferedImage, point.getX(), point.getY(), width, height, (img, infoflags, x1, y1, width, height) -> true);
        point.addX(width);
        point.addY(height);
    }
}
