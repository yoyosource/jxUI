package de.jxui.utils;

import de.jxui.components.Spacer;
import lombok.Getter;
import lombok.ToString;

import java.util.IdentityHashMap;
import java.util.Map;

@Getter
@ToString
public class Spacers {
    private Map<Spacer, Integer> verticalSpacers = new IdentityHashMap<>();
    private Map<Spacer, Integer> horizontalSpacers = new IdentityHashMap<>();
}
