package de.jxui;

import de.jxui.components.*;
import de.jxui.compounds.Button;
import de.jxui.compounds.CenteredStack;
import de.jxui.compounds.Repeat;
import de.jxui.utils.Orientation;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args) {
        /*
        HStack hStack = new HStack(
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
        );
        */
        /*
        hStack = new HStack(
                Image.fromResource("/img.png")
                        .padding()
                        .offset(Direction.TOP, -100)
        );
        */

        /*
        CenteredStack centeredStack = new CenteredStack(
                Image.fromResource("/img.png")
                        .padding(Direction.BOTTOM, -100)
                        .offset(Direction.TOP, -100)
        );
        */

        /*
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
                new Spacer(),
                new StateComponent(
                        userState -> {
                            if (userState.getCanvasWidth() > userState.getCanvasHeight()) {
                                return new Text("Width");
                            } else if (userState.getCanvasHeight() > userState.getCanvasWidth()) {
                                return new Text("Height");
                            } else {
                                return new Text("Same");
                            }
                        }
                ),
                new Spacer(),
                new List<>(
                        s -> {
                            return new VStack(
                                    new Text(s),
                                    new Spacer()
                            );
                        },
                        Arrays.asList("Hello", "World", "Hello World")
                ),
                new Spacer()
        );
        */

        ZStack zStack = new ZStack(
                new Repeat(
                        10,
                        () -> new VStack(new Divider(), new Spacer())
                ).Prefix(
                        new VStack(new Spacer())
                ),
                new Repeat(
                        Orientation.HORIZONTAL,
                        10,
                        () -> new HStack(new Divider(), new Spacer())
                ).Prefix(
                        new HStack(new Spacer())
                )
        );

        /*
        HStack hStack = new HStack(
                new VStack(
                        new HStack(new Spacer()),
                        new Spacer(),
                        new Divider(),
                        new Spacer()
                ),
                new Divider(),
                new VStack(
                        new Spacer(),
                        new Divider(),
                        new Spacer(),
                        new HStack(new Spacer())
                )
        );
        */

        CenteredStack centeredStack = new CenteredStack(
                new Button(
                        (userState, mouseClickEvent) -> {
                            userState.put("clicks", (int) userState.getOrDefault("clicks", 0) + 1);
                            return true;
                        },
                        new StateComponent(
                                userState -> new Text("Clicks: " + userState.getOrDefault("clicks", 0))
                        )
                )
        );

        JxUI jxUI = new JxUI(centeredStack);
        jxUI.setDebug(false);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                jxUI.draw(this);
            }
        };
        // jxUI.addMouseListener(canvas);
        // jxUI.addKeyListener(jFrame);
        jxUI.addListenersTo(canvas);
        canvas.setSize(4 * 100, 3 * 100);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
