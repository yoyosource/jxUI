package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// TODO: cropping, resizing, (overlay)
public class Image extends Element<Image> {

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
    public Size size() {
        return new Size(bufferedImage.getWidth(), bufferedImage.getHeight()).add(padding);
    }

    @Override
    public void draw(Graphics2D g, DrawState drawState, Point point) {
        g.drawImage(bufferedImage, point.getX() + padding.getLeft() + offset.getLeft(), point.getY() + padding.getTop() + offset.getTop(), (img, infoflags, x, y, width, height) -> true);
        point.add(actualSize(g, drawState));
    }
}
