package de.jxui.components;

import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotate extends Element<Rotate> implements Component {

    private double theta;
    private Component component;

    public Rotate(double theta, Component component) {
        this.theta = theta;
        this.component = component;
    }

    public Rotate setTheta(double theta) {
        this.theta = theta;
        return this;
    }

    public Rotate setDegree(double degree) {
        this.theta = Math.toRadians(degree);
        return this;
    }

    public double getDegree() {
        return Math.toDegrees(theta);
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

    // TODO: finish this stuff
    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        component.event(userState, drawState, point, event);
    }

    // TODO: finish this stuff
    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        Size size = drawState.getSizeMap().get(this);
        Size rotatedSize = new Size(0, 0);
        rotatedSize.setWidth((int) (size.getWidth() * Math.cos(theta) + size.getHeight() * Math.sin(theta)));
        rotatedSize.setHeight((int) (size.getHeight() * Math.cos(theta) + size.getWidth() * Math.sin(theta)));
        System.out.println(size + " " + rotatedSize);

        BufferedImage source = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D sourceGraphics = (Graphics2D) source.getGraphics();
        sourceGraphics.setColor(Color.BLUE);
        sourceGraphics.fillRect(0, 0, source.getWidth(), source.getHeight());
        sourceGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        sourceGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        component.draw(sourceGraphics, userState, drawState, new Point(0, 0));
        System.out.println("Source drawn");

        BufferedImage destination = new BufferedImage(rotatedSize.getWidth(), rotatedSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D destinationGraphics = (Graphics2D) destination.getGraphics();
        destinationGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        destinationGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        destinationGraphics.rotate(theta);
        System.out.println((int) (source.getHeight() * Math.sin(theta)));
        destinationGraphics.drawImage(source, (int) (source.getHeight() * Math.sin(theta)), - (int) (source.getHeight() * Math.sin(theta)), source.getWidth(), source.getHeight(), (img, infoflags, x1, y1, width, height) -> true);
        System.out.println("Destination drawn");

        g.drawImage(destination, point.getX(), point.getY(), destination.getWidth(), destination.getHeight(), (img, infoflags, x1, y1, width, height) -> true);
        System.out.println("Drawn");
        point.addX(rotatedSize.getWidth());
        point.addY(rotatedSize.getHeight());
    }
}
