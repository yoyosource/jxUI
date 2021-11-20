package de.jxui.events;

import de.jxui.utils.Point;
import lombok.Getter;
import lombok.ToString;

import java.awt.event.MouseEvent;

@Getter
@ToString
public class ClickEvent implements Event<ClickEvent> {

    private MouseButton button;
    private Point point;
    private int modifier;

    private ClickEvent(ClickEvent clickEvent) {
        this.button = clickEvent.button;
        this.point = clickEvent.point.copy();
        this.modifier = clickEvent.modifier;
    }

    public ClickEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getID() != MouseEvent.MOUSE_CLICKED) {
            throw new SecurityException();
        }
        button = switch (mouseEvent.getButton()) {
            case 1 -> MouseButton.LEFT;
            case 2 -> MouseButton.MIDDLE;
            case 3 -> MouseButton.RIGHT;
            default -> null;
        };
        point = new Point(mouseEvent.getX(), mouseEvent.getY());
        modifier = mouseEvent.getModifiersEx();
    }

    @Override
    public ClickEvent copy() {
        return new ClickEvent(this);
    }
}
