package de.jxui.events;

import de.jxui.utils.Point;
import lombok.Getter;
import lombok.ToString;

import java.awt.event.MouseEvent;

@Getter
@ToString
public class MouseMoveEvent implements Event {

    private Point point;

    public MouseMoveEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getID() != MouseEvent.MOUSE_MOVED) {
            throw new SecurityException();
        }
        point = new Point(mouseEvent.getX(), mouseEvent.getY());
    }
}
