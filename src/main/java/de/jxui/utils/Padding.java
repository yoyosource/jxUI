package de.jxui.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Padding {
    private int left = 5;
    private int right = 5;
    private int top = 5;
    private int bottom = 5;
}
