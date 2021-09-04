package de.jxui.complex;

import de.jxui.action.ButtonAction;
import de.jxui.components.*;
import de.jxui.compounds.event.Button;
import de.jxui.compounds.Centered;
import de.jxui.compounds.Repeat;
import de.jxui.utils.JxFrame;
import de.jxui.utils.Orientation;

public class TestTicTacToe {

    public static void main(String[] args) {
        Centered centered = new Centered(
                new Repeat(Orientation.HORIZONTAL, 3, x -> {
                    return new Repeat(Orientation.VERTICAL, 3, y -> {
                        return new Button(
                                cellSet("" + x + y),
                                new StateComponent(userState -> {
                                    return new TextTemplate("{" + x + y + "|' '}")
                                            .size((int) Math.sqrt(userState.getCanvasHeight() + userState.getCanvasWidth()));
                                })
                        );
                    }).Prefix(Spacer::new).Joining(Spacer::new).Suffix(Spacer::new);
                }).Prefix(Spacer::new).Joining(Spacer::new).Suffix(Spacer::new)
        );
        new JxFrame(centered);
    }

    private static ButtonAction cellSet(String cellKey) {
        System.out.println(cellKey);
        return (userState, event) -> {
            if (userState.containsKey(cellKey)) {
                return false;
            }
            String current;
            if (userState.containsKey("player")) {
                current = (String) userState.get("player");
            } else {
                current = "x";
            }

            if (current.equals("x")) {
                userState.put("player", "o");
            } else {
                userState.put("player", "x");
            }

            userState.put(cellKey, current);
            return true;
        };
    }
}
