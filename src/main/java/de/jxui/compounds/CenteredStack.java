package de.jxui.compounds;

import de.jxui.components.*;

public class CenteredStack extends Group<CenteredStack> {
    public CenteredStack(Component component) {
        super(new VStack(new Spacer(), new HStack(new Spacer(), component, new Spacer()), new Spacer()));
    }
}
