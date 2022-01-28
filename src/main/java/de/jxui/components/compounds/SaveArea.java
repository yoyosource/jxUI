package de.jxui.components.compounds;

import de.jxui.components.*;

public class SaveArea extends Group<SaveArea> {

    public SaveArea(Component inner) {
        this(5, inner);
    }

    public SaveArea(int padding, Component inner) {
        super(new VStack(
                new Spacer(padding),
                new HStack(
                        new Spacer(padding),
                        inner,
                        new Spacer(padding)
                ),
                new Spacer(padding)
        ));
    }
}
