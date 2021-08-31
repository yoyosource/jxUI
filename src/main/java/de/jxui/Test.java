package de.jxui;

import de.jxui.components.*;
import de.jxui.components.Image;
import de.jxui.compounds.CenteredStack;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Test {

    public static void main(String[] args) {
        /*
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
                        new HStack(
                                new Spacer(),
                                new Text("North")
                                        .size(20),
                                new Spacer()
                        ),
                        new Spacer(),
                        new Text("Earth")
                                .size(100),
                        new Spacer(),
                        new HStack(
                                new Spacer(),
                                new Text("South")
                                        .size(20),
                                new Spacer()
                        ),
                        new Spacer(5)
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
        */

        HStack hStack = new HStack(
                new Text("1"),
                new Spacer(),
                new Text("2"),
                new VStack(
                        new Text("3"),
                        new HStack(
                                new Text("4"),
                                new Text("Hello World")
                                        .padding(),
                                new Text("5"),
                                new Spacer(),
                                new Text("6")
                        ),
                        new Text("7")
                ),
                new Text("8")
        );
        hStack = new HStack(
                new Image(new File("/Users/jojo/IdeaProjects/jxUI/src/main/resources/img.png"))
                        .padding()
        );
        CenteredStack centeredStack = new CenteredStack(
                new Image(Test.class.getResourceAsStream("/img.png"))
        );

        JxUI jxUI = new JxUI(centeredStack);

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
