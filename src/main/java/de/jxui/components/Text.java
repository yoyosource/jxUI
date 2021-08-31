package de.jxui.components;

import de.jxui.utils.Padding;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.State;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;

@Accessors(chain = true)
public class Text implements Component, ComponentPadding<Text> {

    private String text;

    @Setter
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    @Setter
    private Color color = new Color(0, 0, 0);

    private Padding padding;

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
    public Text padding() {
        padding = new Padding();
        return this;
    }

    @Override
    public Text padding(Padding padding) {
        this.padding = padding;
        return this;
    }

    @Override
    @SuppressWarnings("deprecated")
    public Size size() {
        FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        return new Size(fontMetrics.stringWidth(text), fontMetrics.getHeight()).add(padding);
    }

    @Override
    public void draw(Graphics2D g, State state, Point point) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, point.getX(), point.getY() + size().getHeight());
        point.add(actualSize(g, state));
    }
}