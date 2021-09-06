package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// TODO: resizing, (overlay)
public class Image extends Element<Image> {

    private BufferedImage source;
    private BufferedImage current;

    public Image(File file) {
        try {
            source = ImageIO.read(file);
            current = source.getSubimage(0, 0, source.getWidth(), source.getHeight());
        } catch (IOException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    public Image(InputStream inputStream) {
        try {
            source = ImageIO.read(inputStream);
            current = source.getSubimage(0, 0, source.getWidth(), source.getHeight());
        } catch (IOException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    public static Image fromResource(String resourceIdentifier) {
        if (resourceIdentifier.startsWith("/")) {
            return new Image(Image.class.getResourceAsStream(resourceIdentifier));
        } else {
            return new Image(Image.class.getResourceAsStream("/" + resourceIdentifier));
        }
    }

    public static Image fromFile(String fileIdentifier) {
        return new Image(new File(fileIdentifier));
    }

    public Image original() {
        current = null;
        return this;
    }

    public Image crop(int x, int y, int width, int height) {
        if (current == null) {
            current = source.getSubimage(x, y, width, height);
        } else {
            current = current.getSubimage(x, y, width, height);
        }
        return this;
    }

    public Image upscale(int factor) {
        if (factor <= 0) throw new SecurityException();
        return upscale(factor, factor);
    }

    public Image upscale(int widthFactor, int heightFactor) {
        if (widthFactor <= 0) throw new SecurityException();
        if (heightFactor <= 0) throw new SecurityException();
        if (current == null) {
            current = upscale(source, widthFactor, heightFactor);
        } else {
            current = upscale(current, widthFactor, heightFactor);
        }
        return this;
    }

    private BufferedImage upscale(BufferedImage source, int wFactor, int hFactor) {
        BufferedImage bufferedImage = new BufferedImage(source.getWidth() * wFactor, source.getHeight() * hFactor, source.getType());
        WritableRaster sourceRaster = source.getRaster();
        WritableRaster destinationRaster = bufferedImage.getRaster();
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                int[] ints = sourceRaster.getPixel(x, y, (int[]) null);
                for (int xi = 0; xi < wFactor; xi++) {
                    for (int yi = 0; yi < hFactor; yi++) {
                        destinationRaster.setPixel(x * wFactor + xi, y * hFactor + yi, ints);
                    }
                }
            }
        }
        return bufferedImage;
    }

    public Image downscale(int factor) {
        if (factor <= 0) throw new SecurityException();
        return downscale(factor, factor);
    }

    public Image downscale(int widthFactor, int heightFactor) {
        if (widthFactor <= 0) throw new SecurityException();
        if (heightFactor <= 0) throw new SecurityException();
        if (current == null) {
            current = downscale(source, widthFactor, heightFactor);
        } else {
            current = downscale(current, widthFactor, heightFactor);
        }
        return this;
    }

    private BufferedImage downscale(BufferedImage source, int wFactor, int hFactor) {
        BufferedImage bufferedImage = new BufferedImage(source.getWidth() / wFactor, source.getHeight() / hFactor, source.getType());
        WritableRaster sourceRaster = source.getRaster();
        WritableRaster destinationRaster = bufferedImage.getRaster();
        for (int x = 0; x < source.getWidth() / wFactor; x++) {
            for (int y = 0; y < source.getHeight() / hFactor; y++) {
                int[] ints = sourceRaster.getPixel(x * wFactor, y * hFactor, (int[]) null);
                for (int xi = 0; xi < wFactor; xi++) {
                    for (int yi = 0; yi < hFactor; yi++) {
                        if (xi == 0 && yi == 0) continue;
                        int[] current = sourceRaster.getPixel(x * wFactor + xi, y * hFactor + yi, (int[]) null);
                        for (int i = 0; i < current.length; i++) {
                            ints[i] = ints[i] + current[i];
                        }
                    }
                }
                for (int i = 0; i < ints.length; i++) {
                    ints[i] = ints[i] / (wFactor * hFactor);
                }
                destinationRaster.setPixel(x, y, ints);
            }
        }
        return bufferedImage;
    }

    @Override
    public Size size(UserState userState) {
        if (current == null) {
            return new Size(source.getWidth(), source.getHeight()).add(padding);
        } else {
            return new Size(current.getWidth(), current.getHeight()).add(padding);
        }
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        if (current == null) {
            g.drawImage(source, point.getX() + offset.getLeft() + padding.getLeft(), point.getY() + offset.getTop() + padding.getTop(), (img, infoflags, x, y, width, height) -> true);
        } else {
            g.drawImage(current, point.getX() + offset.getLeft() + padding.getLeft(), point.getY() + offset.getTop() + padding.getTop(), (img, infoflags, x, y, width, height) -> true);
        }
        debugDraw(g, drawState, point.add(padding));
        point.add(drawState.getSizeMap().get(this));
    }
}
