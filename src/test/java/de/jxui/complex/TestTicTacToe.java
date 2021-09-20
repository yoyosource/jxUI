package de.jxui.complex;

import de.jxui.action.Action;
import de.jxui.action.UserStatePredicate;
import de.jxui.components.*;
import de.jxui.components.compounds.Centered;
import de.jxui.components.compounds.Grid;
import de.jxui.components.compounds.If;
import de.jxui.components.event.Button;
import de.jxui.events.ClickEvent;
import de.jxui.utils.JxFrame;

public class TestTicTacToe {

    public static void main(String[] args) {
        Centered centered = new Centered(
                new VStack(
                        new Grid(3, 3, (x, y) -> {
                            return new Button(
                                    cellSet("" + x + y),
                                    new StateComponent<>(
                                            new TextTemplate("{" + x + y + "|' '}"),
                                            (userState, textTemplate) -> {
                                                textTemplate.size(Math.min(userState.getCanvasHeight(), userState.getCanvasWidth()) / 10);
                                            }
                                    )
                            );
                        }).Prefix(Spacer::new).Joining(Spacer::new).Suffix(Spacer::new).Static(),
                        new If(
                                UserStatePredicate.containsAll("00", "01", "02", "10", "11", "12", "20", "21", "22"),
                                new Button(
                                        (userState, event) -> {
                                            userState.clear();
                                            return true;
                                        },
                                        new Text("Clear")
                                )
                        )
                )
        );
        new JxFrame(centered);
    }

    private static Action<ClickEvent> cellSet(String cellKey) {
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
