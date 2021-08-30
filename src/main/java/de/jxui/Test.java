package de.jxui;

import de.jxui.components.*;

public class Test {

    public static void main(String[] args) {
        VStack vStack = new VStack(
                new Text("Hello World")
                        .size(20),
                new Text("Hello World")
                        .size(20),
                new HStack(
                        new Text("Hello"),
                        new Spacer(),
                        new Text("World")
                ),
                new ZStack(
                        new Text("Hello World"),
                        new Text("Hello World")
                                .size(13)
                ),
                new ZStack(
                        new Text("Hello World"),
                        new Text("Hello World")
                                .size(13)
                )
        );
        System.out.println(vStack.size());
    }
}
