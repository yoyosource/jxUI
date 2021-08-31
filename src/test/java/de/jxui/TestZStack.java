package de.jxui;

import de.jxui.components.Text;
import de.jxui.components.ZStack;
import de.jxui.utils.JxFrame;

public class TestZStack {

    public static void main(String[] args) {
        TestZStack testZStack = new TestZStack();
        testZStack.testSimpleZStack();
    }

    private void testSimpleZStack() {
        ZStack zStack = new ZStack(
                new Text("Hello"),
                new Text("World")
        );
        new JxFrame(zStack);
    }
}
