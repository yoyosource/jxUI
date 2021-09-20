package de.jxui.components.compounds;

import de.jxui.components.Component;
import de.jxui.components.behaviour.Joining;
import de.jxui.components.behaviour.Prefix;
import de.jxui.components.behaviour.Suffix;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Table implements Component, Prefix<Table>, Suffix<Table>, Joining<Table> {

    private int rows = 1;
    private int columns = 1;

    private boolean regen = false;

    private Supplier<Component> prefix;
    private Supplier<Component> joining;
    private Supplier<Component> suffix;

    private Map<Integer, Component> componentMap = new HashMap<>();

    public Table() {
    }

    public Table addRow() {
        if (rows == 1024) {
            throw new SecurityException("Rows cannot be higher than 1024");
        }
        rows++;
        regen = true;
        return this;
    }

    public Table addColumn() {
        if (columns == 1024) {
            throw new SecurityException("Columns cannot be higher than 1024");
        }
        columns++;
        regen = true;
        return this;
    }

    private int checkIndex(int row, int column) {
        if (row > rows || row < 0) {
            throw new SecurityException("row is out of table range");
        }
        if (column > columns || column < 0) {
            throw new SecurityException("column is out of table range");
        }
        return row * 1024 + column;
    }

    public Table set(int row, int column, Component component) {
        int index = checkIndex(row, column);
        componentMap.put(index, component);
        regen = true;
        return this;
    }

    public Table remove(int row, int column) {
        int index = checkIndex(row, column);
        componentMap.remove(index);
        return this;
    }

    @Override
    public Table Prefix(Supplier<Component> component) {
        this.prefix = component;
        return this;
    }

    @Override
    public Table Joining(Supplier<Component> component) {
        this.joining = component;
        return this;
    }

    @Override
    public Table Suffix(Supplier<Component> component) {
        this.suffix = component;
        return this;
    }

    @Override
    public void cleanUp() {
        Component.super.cleanUp();
    }

    @Override
    public Size size(UserState userState) {
        return null;
    }

    @Override
    public void size(Size size, UserState userState, DrawState drawState) {
        Component.super.size(size, userState, drawState);
    }

    @Override
    public int spacers(UserState userState, Orientation orientation) {
        return Component.super.spacers(userState, orientation);
    }

    @Override
    public void event(UserState userState, DrawState drawState, Point point, Event event) {
        Component.super.event(userState, drawState, point, event);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        Component.super.draw(g, userState, drawState, point);
    }
}
