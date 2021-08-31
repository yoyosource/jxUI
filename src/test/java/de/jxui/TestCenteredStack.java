package de.jxui;

import de.jxui.components.HStack;
import de.jxui.components.Spacer;
import de.jxui.components.Text;
import de.jxui.compounds.CenteredStack;
import de.jxui.utils.JxFrame;

public class TestCenteredStack {

    public static void main(String[] args) {
        TestCenteredStack testCenteredStack = new TestCenteredStack();
        testCenteredStack.testSimpleCenteredStack();
    }

    private void testSimpleCenteredStack() {
        CenteredStack centeredStack = new CenteredStack(
                new HStack(
                        new Text("Hello"),
                        new Spacer(),
                        new Text("World")
                )
        );
        new JxFrame(centeredStack);
    }
}
