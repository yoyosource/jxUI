package de.jxui.events;

import de.jxui.utils.Point;
import lombok.Getter;
import lombok.ToString;

import java.awt.event.MouseEvent;

@Getter
@ToString
public class DragEvent implements Event {

    private MouseButton button;
    private Point point;
    private int modifier;

    public DragEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getID() != MouseEvent.MOUSE_DRAGGED) {
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
}
