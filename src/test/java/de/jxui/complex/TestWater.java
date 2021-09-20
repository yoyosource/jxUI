package de.jxui.complex;

import de.jxui.components.Image;
import de.jxui.components.StateComponent;
import de.jxui.components.compounds.Centered;
import de.jxui.utils.JxFrame;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class TestWater {

    private static int width = 100;
    private static int height = 100;

    public static void main(String[] args) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Centered centered = new Centered(
                new StateComponent<>(
                        userState -> new Image(bufferedImage).upscale(3)
                )
        );
        JxFrame jxFrame = new JxFrame(centered);

        Thread thread = new Thread(() -> {
            double[] grid = new double[width * height];

            while (true) {
                grid[getIndex(width / 2, height / 2)] += 1000;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                double[] tempGrid = new double[grid.length];
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int index = getIndex(x, y);
                        double value = grid[index];
                        double factor = 0.1;
                        tempGrid[index] += value * factor;
                        value = value * (1.0 - factor);

                        List<Integer> indices = new ArrayList<>();
                        if (x > 0 && value > grid[getIndex(x - 1, y)]) {
                            indices.add(getIndex(x - 1, y));
                        }
                        if (x < width - 1 && value > grid[getIndex(x + 1, y)]) {
                            indices.add(getIndex(x + 1, y));
                        }
                        if (y > 0 && value > grid[getIndex(x, y - 1)]) {
                            indices.add(getIndex(x, y - 1));
                        }
                        if (y < height - 1 && value > grid[getIndex(x, y + 1)]) {
                            indices.add(getIndex(x, y + 1));
                        }

                        for (int current : indices) {
                            tempGrid[current] += value / indices.size();
                        }
                    }
                }
                grid = tempGrid;

                System.out.println("Write");
                WritableRaster writableRaster = bufferedImage.getRaster();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int index = getIndex(x, y);
                        double value = grid[index];
                        // System.out.println("f(" + x + "," + y + ")=" + value);
                        if (value > 255) {
                            value = 255;
                        }
                        if (value < 0) {
                            value = 0;
                        }
                        writableRaster.setPixel(x, y, new int[]{255 - (int) value, 255 - (int) value, 255});
                    }
                }
                bufferedImage.setData(writableRaster);

                jxFrame.getJxUI().getUserState().put("redraw", true);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static int getIndex(int x, int y) {
        return y * width + x;
    }
}
