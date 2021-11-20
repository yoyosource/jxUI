package de.jxui.events;

import de.jxui.utils.Point;
import lombok.Getter;
import lombok.ToString;

import java.awt.event.MouseEvent;

@Getter
@ToString
public class MoveEvent implements Event<MoveEvent> {

    private Point point;

    private MoveEvent(MoveEvent moveEvent) {
        this.point = moveEvent.getPoint().copy();
    }

    public MoveEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getID() != MouseEvent.MOUSE_MOVED) {
            throw new SecurityException();
        }
        point = new Point(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public MoveEvent copy() {
        return new MoveEvent(this);
    }
}
