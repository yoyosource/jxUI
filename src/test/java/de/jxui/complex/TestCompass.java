package de.jxui.complex;

import de.jxui.components.HStack;
import de.jxui.components.Spacer;
import de.jxui.components.Text;
import de.jxui.components.VStack;
import de.jxui.components.compounds.If;
import de.jxui.utils.JxFrame;

public class TestCompass {

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
                        new HStack(
                                new Spacer(),
                                new Text("North")
                                        .size(20),
                                new Spacer()
                        ),
                        new Spacer(),
                        new If(
                                userState -> userState.getCanvasWidth() > 600,
                                new HStack(
                                        new Spacer(),
                                        new Text("Earth")
                                                .size(100),
                                        new Spacer()
                                )
                        ),
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
        new JxFrame(hStack);
    }
}
