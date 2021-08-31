package de.jxui;

import de.jxui.components.Spacer;
import de.jxui.components.Text;
import de.jxui.components.VStack;
import de.jxui.utils.JxFrame;

public class TestVStack {

    public static void main(String[] args) {
        TestVStack testHStack = new TestVStack();
        testHStack.testSimpleCenteredVStack();
    }

    private void testSimpleVStack() {
        VStack vStack = new VStack(
                new Text("Hello"),
                new Spacer(),
                new Text("World")
        );
        new JxFrame(vStack);
    }

    private void testSimpleCenteredVStack() {
        VStack vStack = new VStack(
                new Spacer(),
                new Text("Hello"),
                new Spacer(5),
                new Text("World"),
                new Spacer()
        );
        new JxFrame(vStack);
    }
}
