package de.jxui.components.compounds;

import de.jxui.components.*;
import de.jxui.utils.Orientation;
import lombok.NonNull;

/**
 * Centers the wrapped component in regards to the outer component, either explicitly in one orientation or both.
 */
public class Centered extends Group<Centered> {
    public Centered(@NonNull Component component) {
        super(new VStack(new Spacer(), new HStack(new Spacer(), component, new Spacer()), new Spacer()));
    }

    public Centered(Orientation orientation, @NonNull Component component) {
        super(orientation == Orientation.VERTICAL ?
                new VStack(new Spacer(), component, new Spacer()) :
                new HStack(new Spacer(), component, new Spacer()));
    }
}
