package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Render a component either flipped horizontally or vertically.
 */
public class Flip extends Element<Flip> implements Component {

    private Orientation orientation;
    private Component component;

    public Flip(Component component) {
        this(Orientation.HORIZONTAL, component);
    }

    public Flip(Orientation orientation, Component component) {
        this.orientation = orientation;
        this.component = component;
    }

    @Override
    public void cleanUp() {
        component.cleanUp();
    }

    @Override
    public Size size(UserState userState) {
        return component.size(userState);
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        component.size(size, userState, drawState);
        drawState.getSizeMap().put(this, drawState.getSizeMap().get(component));
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return component.spacers(userState, orientation);
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        if (orientation == Orientation.VERTICAL) {

        } else {

        }
        // TODO: implement flipping behaviour
    }

    private int flip(int min, int size, int point) {
        return size - (point - min);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        Size size = drawState.getSizeMap().get(this);
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        component.draw(graphics, userState, drawState, new Point(0, 0));
        bufferedImage.setData(flip(bufferedImage.getRaster(), orientation));
        g.drawImage(bufferedImage, point.getX(), point.getY(), size.getWidth(), size.getHeight(), (img, infoflags, x1, y1, width, height) -> true);
        point.add(size);
    }

    private Raster flip(WritableRaster raster, Orientation orientation) {
        if (orientation == Orientation.VERTICAL) {
            for (int x = 0; x < raster.getWidth(); x++) {
                int[] ints = raster.getPixels(x, 0, 1, raster.getHeight(), (int[]) null);
                flip(ints, raster.getNumBands());
                raster.setPixels(x, 0, 1, raster.getHeight(), ints);
            }
        } else {
            for (int y = 0; y < raster.getHeight(); y++) {
                int[] ints = raster.getPixels(0, y, raster.getWidth(), 1, (int[]) null);
                flip(ints, raster.getNumBands());
                raster.setPixels(0, y, raster.getWidth(), 1, ints);
            }
        }
        return raster;
    }

    private void flip(int[] ints, int numBands) {
        for (int i = 0; i < ints.length / numBands / 2; i++) {
            for (int n = 0; n < numBands; n++) {
                int index1 = i * numBands + n;
                int index2 = ints.length - (i + 1) * numBands + n;
                int i1 = ints[index1];
                int i2 = ints[index2];
                ints[index1] = i2;
                ints[index2] = i1;
            }
        }
    }
}
