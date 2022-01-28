package de.jxui;

import de.jxui.components.Text;
import de.jxui.parser.JxUIParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Test2 {

    public static void main(String[] args) {
        JxUIFile jxUIFile = new JxUIFile(new File("C:\\Users\\van-beeck\\IdeaProjects\\jxUI\\src\\main\\resources\\Test.jxui"));
        JxUIParser.addComponent(Text.class);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                jxUIFile.draw(this);
            }
        };
        // jxUI.addMouseListener(canvas);
        // jxUI.addKeyListener(jFrame);
        jxUIFile.addListenersTo(canvas);
        canvas.setSize(4 * 100, 3 * 100);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
