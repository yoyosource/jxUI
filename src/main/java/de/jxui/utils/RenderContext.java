package de.jxui.utils;

import de.jxui.components.Spacer;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class RenderContext {
    private List<Spacer> verticalSpacer = new ArrayList<>();
    private List<Spacer> horizontalSpacer = new ArrayList<>();
}
