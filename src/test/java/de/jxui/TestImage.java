package de.jxui;

import de.jxui.components.Image;
import de.jxui.components.compounds.Centered;
import de.jxui.utils.JxFrame;

public class TestImage {

    public static void main(String[] args) {
        TestImage testImage = new TestImage();
        testImage.testSimpleCenteredImage();
    }

    private void testSimpleImage() {
        Image image = new Image(TestImage.class.getResourceAsStream("/img.png"));
        new JxFrame(image);
    }

    private void testSimpleCenteredImage() {
        Centered centeredStack = new Centered(
                new Image(TestImage.class.getResourceAsStream("/img.png"))
                        .upscale(10)
        );
        new JxFrame(centeredStack);
    }
}
