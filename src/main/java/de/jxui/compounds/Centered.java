package de.jxui.compounds;

import de.jxui.components.*;

public class Centered extends Group<Centered> {
    public Centered(Component component) {
        super(new VStack(new Spacer(), new HStack(new Spacer(), component, new Spacer()), new Spacer()));
    }
}
