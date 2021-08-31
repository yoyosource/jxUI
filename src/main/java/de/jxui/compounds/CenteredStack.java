package de.jxui.compounds;

import de.jxui.components.*;
import de.jxui.utils.Padding;

public class CenteredStack extends Element<CenteredStack> {
    public CenteredStack(Component component) {
        super(new VStack(new Spacer(), new HStack(new Spacer(), component, new Spacer()), new Spacer()));
    }

    @Override
    public CenteredStack padding() {
        padding = new Padding();
        return this;
    }

    @Override
    public CenteredStack padding(Padding padding) {
        this.padding = padding;
        return this;
    }
}
