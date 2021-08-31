package de.jxui;

import de.jxui.components.*;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args) {
        HStack hStack = new HStack(
                new Spacer(5),
                new VStack(
                        new Spacer(),
                        new Text("West")
                                .size(20),
                        new Spacer()
                ),
                new Spacer(),
                new VStack(
                        new Text("North")
                                .size(20),
                        new Spacer(),
                        new Text("Earth")
                                .size(100),
                        new Spacer(),
                        new Text("South")
                                .size(20)
                ),
                new Spacer(),
                new VStack(
                        new Spacer(),
                        new Text("East")
                                .size(20),
                        new Spacer()
                ),
                new Spacer(5)
        );

        JxUI jxUI = new JxUI(hStack);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                jxUI.draw(this);
            }
        };
        canvas.setSize(4 * 100, 3 * 100);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
