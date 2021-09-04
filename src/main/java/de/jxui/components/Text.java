package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.function.Consumer;

@Accessors(chain = true)
public class Text extends Element<Text> {

    protected String text;

    @Setter
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    @Setter
    private Color color = new Color(0, 0, 0);

    private Size minSize = new Size(0, 0);

    public Text() {
        this("");
    }

    public Text(String text) {
        this.text = text;
    }

    public Text size(int size) {
        font = new Font(font.getFontName(), font.getStyle(), size);
        return this;
    }

    public Text style(int style) {
        font = new Font(font.getFontName(), style, font.getSize());
        return this;
    }

    public Text font(String name) {
        font = new Font(name, font.getStyle(), font.getSize());
        return this;
    }

    public Text color(Color color) {
        this.color = color;
        return this;
    }

    public Text color(int r, int g, int b) {
        color = new Color(r, g, b);
        return this;
    }

    public Text minSize(int width, int height) {
        if (width < 0) width = 0;
        if (height < 0) height = 0;
        minSize.setWidth(width);
        minSize.setHeight(height);
        return this;
    }

    @Override
    @SuppressWarnings("deprecated")
    public Size size(UserState userState) {
        FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        Size size = new Size(0, 0);
        split(text, '\n', s -> {
            size.setWidth(Math.max(size.getWidth(), fontMetrics.stringWidth(s)));
            size.setHeight(size.getHeight() + fontMetrics.getHeight());
        });
        return size.merge(minSize).add(padding);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        g.setColor(color);
        g.setFont(font);
        Point current = point.copy();
        split(text, '\n', s -> {
            current.addY(g.getFontMetrics().getHeight());
            g.drawString(s, current.getX() + offset.getLeft() + padding.getLeft(), current.getY() + offset.getTop() + padding.getTop());
        });
        debugDraw(g, drawState, point.add(offset).add(padding));
        point.add(drawState.getSizeMap().get(this));
    }

    private void split(String s, char delimiter, Consumer<String> stringConsumer) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == delimiter) {
                stringConsumer.accept(st.toString());
                st = new StringBuilder();
                if (i == s.length() - 1) {
                    stringConsumer.accept(st.toString());
                }
            } else {
                st.append(s.charAt(i));
            }
        }
        if (st.length() > 0) {
            stringConsumer.accept(st.toString());
        }
    }
}
