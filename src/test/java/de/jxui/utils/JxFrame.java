package de.jxui.utils;

import de.jxui.JxUI;
import de.jxui.components.Component;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class JxFrame {

    @Getter
    private JxUI jxUI;

    public JxFrame(Component component) {
        jxUI = new JxUI(component);
        jxUI.setDebug(true);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                jxUI.draw(this);
            }
        };
        jxUI.addListenersTo(canvas);
        canvas.setSize(4 * 100, 3 * 100);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
