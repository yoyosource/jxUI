package de.jxui;

import de.jxui.components.Image;
import de.jxui.components.*;
import de.jxui.compounds.CenteredStack;
import de.jxui.compounds.Switch;
import de.jxui.utils.Direction;
import de.jxui.utils.UserState;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args) {
        /*HStack hStack = new HStack(
                new Text("1"),
                new Spacer(),
                new Text("2"),
                new Switch()
                        .Case(
                                UserState::linux,
                                new Text("Linux")
                        )
                        .Case(
                                UserState::mac,
                                new Text("Linux")
                        )
                        .Case(
                                UserState::windows,
                                new Text("Windows")
                        )
                        .Default(
                                new Text("Other")
                        ),
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
        );*/
        /*
        hStack = new HStack(
                Image.fromResource("/img.png")
                        .padding()
                        .offset(Direction.TOP, -100)
        );
        */

        CenteredStack centeredStack = new CenteredStack(
                Image.fromResource("/img.png")
                        .padding(Direction.BOTTOM, -100)
                        .offset(Direction.TOP, -100)
        );

        HStack hStack = new HStack(
                new Spacer(),
                new Switch()
                        .Case(
                                UserState::linux,
                                new Text("Linux")
                        )
                        .Case(
                                UserState::mac,
                                new Text("Mac")
                        )
                        .Case(
                                UserState::windows,
                                new Text("Windows")
                        )
                        .Default(
                                new Text("Other")
                        ),
                new Spacer()
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
