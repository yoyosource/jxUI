package de.jxui.components;

import de.jxui.utils.Padding;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Image implements Component, ComponentPadding<Image> {

    private Padding padding = null;
    private BufferedImage bufferedImage;

    public Image(File file) {
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    public Image(InputStream inputStream) {
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    @Override
    public Image padding() {
        padding = new Padding();
        return this;
    }

    @Override
    public Image padding(Padding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public Size size() {
        return new Size(bufferedImage.getWidth(), bufferedImage.getHeight()).add(padding);
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        g.drawImage(bufferedImage, point.getX() + (padding != null ? padding.getLeft() : 0), point.getY() + (padding != null ? padding.getTop() : 0), (img, infoflags, x, y, width, height) -> true);
        point.add(actualSize(g, state));
    }
}
