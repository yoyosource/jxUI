package de.jxui;

import de.jxui.components.HStack;
import de.jxui.components.Spacer;
import de.jxui.components.Text;
import de.jxui.utils.JxFrame;

public class TestHStack {

    public static void main(String[] args) {
        TestHStack testHStack = new TestHStack();
        testHStack.testSimpleCenteredHStack();
    }

    private void testSimpleHStack() {
        HStack hStack = new HStack(
                new Text("Hello"),
                new Spacer(),
                new Text("World")
        );
        new JxFrame(hStack);
    }

    private void testSimpleCenteredHStack() {
        HStack hStack = new HStack(
                new Spacer(),
                new Text("Hello"),
                new Spacer(5),
                new Text("World"),
                new Spacer()
        );
        new JxFrame(hStack);
    }
}
