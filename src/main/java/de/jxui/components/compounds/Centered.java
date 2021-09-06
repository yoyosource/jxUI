package de.jxui.components.compounds;

import de.jxui.components.*;
import lombok.NonNull;

public class Centered extends Group<Centered> {
    public Centered(@NonNull Component component) {
        super(new VStack(new Spacer(), new HStack(new Spacer(), component, new Spacer()), new Spacer()));
    }
}
