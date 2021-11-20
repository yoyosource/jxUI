package de.jxui.events;

import de.jxui.utils.Point;
import lombok.Getter;
import lombok.ToString;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@Getter
@ToString
public class ScrollEvent implements Event<ScrollEvent> {

    private int unitsToScroll;
    private int scrollAmount;
    private double preciseWheelRotation;
    private int wheelRotation;
    private Point point;
    private int modifier;

    private ScrollEvent(ScrollEvent scrollEvent) {
        this.unitsToScroll = scrollEvent.unitsToScroll;
        this.scrollAmount = scrollEvent.scrollAmount;
        this.preciseWheelRotation = scrollEvent.preciseWheelRotation;
        this.wheelRotation = scrollEvent.wheelRotation;
        this.point = scrollEvent.point.copy();
        this.modifier = scrollEvent.modifier;
    }

    public ScrollEvent(MouseWheelEvent mouseEvent) {
        if (mouseEvent.getID() != MouseEvent.MOUSE_WHEEL) {
            throw new SecurityException();
        }
        unitsToScroll = mouseEvent.getUnitsToScroll();
        scrollAmount = mouseEvent.getScrollAmount();
        preciseWheelRotation = mouseEvent.getPreciseWheelRotation();
        wheelRotation = mouseEvent.getWheelRotation();
        point = new Point(mouseEvent.getX(), mouseEvent.getY());
        modifier = mouseEvent.getModifiersEx();
    }

    @Override
    public ScrollEvent copy() {
        return new ScrollEvent(this);
    }
}
