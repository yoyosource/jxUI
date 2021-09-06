package de.jxui.complex;

import de.jxui.action.Action;
import de.jxui.components.Spacer;
import de.jxui.components.StateComponent;
import de.jxui.components.TextTemplate;
import de.jxui.components.compounds.Centered;
import de.jxui.components.compounds.Repeat;
import de.jxui.components.event.Button;
import de.jxui.events.MouseClickEvent;
import de.jxui.utils.JxFrame;
import de.jxui.utils.Orientation;

public class TestTicTacToe {

    public static void main(String[] args) {
        Centered centered = new Centered(
                new Repeat(Orientation.HORIZONTAL, 3, x -> {
                    return new Repeat(Orientation.VERTICAL, 3, y -> {
                        return new Button(
                                cellSet("" + x + y),
                                new StateComponent<>(
                                        new TextTemplate("{" + x + y + "|' '}"),
                                        (userState, textTemplate) -> {
                                            textTemplate.size(Math.min(userState.getCanvasHeight(), userState.getCanvasWidth()) / 10);
                                        }
                                )
                        );
                    }).Prefix(Spacer::new).Joining(Spacer::new).Suffix(Spacer::new).Static();
                }).Prefix(Spacer::new).Joining(Spacer::new).Suffix(Spacer::new).Static()
        );
        new JxFrame(centered);
    }

    private static Action<MouseClickEvent> cellSet(String cellKey) {
        System.out.println(cellKey);
        return (userState, event) -> {
            if (userState.containsKey(cellKey)) {
                return false;
            }
            String current;
            if (userState.containsKey("player")) {
                current = userState.get("player");
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
