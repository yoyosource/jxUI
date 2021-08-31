package de.jxui.utils;

import de.jxui.JxUI;
import de.jxui.components.Component;

import javax.swing.*;
import java.awt.*;

public class JxFrame {

    public JxFrame(Component component) {
        JxUI jxUI = new JxUI(component);

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
