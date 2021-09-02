package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;

@Accessors(chain = true)
public class Text extends Element<Text> {

    private String text;

    @Setter
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    @Setter
    private Color color = new Color(0, 0, 0);

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

    @Override
    @SuppressWarnings("deprecated")
    public Size size(UserState userState) {
        FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        return new Size(fontMetrics.stringWidth(text), fontMetrics.getHeight()).add(padding);
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, point.getX() + offset.getLeft() + padding.getLeft(), point.getY() + size(null).getHeight() + offset.getTop() + padding.getTop());
        debugDraw(g, drawState, point.add(offset));
        point.add(drawState.getSizeMap().get(this));
    }
}
