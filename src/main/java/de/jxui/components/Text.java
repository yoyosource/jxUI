package de.jxui.components;

import de.jxui.utils.DrawState;
import de.jxui.utils.Point;
import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

@Accessors(chain = true)
public class Text extends Element<Text> {

    private static Map<Font, FontMetrics> fontMetricsCache = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Font, FontMetrics> eldest) {
            return super.size() > 100;
        }
    };

    private String text;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    @Setter
    private Color color = new Color(0, 0, 0);

    private Size minSize = new Size(0, 0);

    private FontMetrics fontMetrics;
    private Size cachedSize = null;

    public Text() {
        this("");
    }

    public Text(String text) {
        this.text = text;
    }

    public void setText(String text) {
        cachedSize = null;
        this.text = text;
    }

    public Text size(int size) {
        cachedSize = null;
        fontMetrics = null;
        font = new Font(font.getFontName(), font.getStyle(), size);
        return this;
    }

    public Text style(int style) {
        cachedSize = null;
        fontMetrics = null;
        font = new Font(font.getFontName(), style, font.getSize());
        return this;
    }

    public Text font(String name) {
        cachedSize = null;
        fontMetrics = null;
        font = new Font(name, font.getStyle(), font.getSize());
        return this;
    }

    public Text setFont(Font font) {
        cachedSize = null;
        fontMetrics = null;
        this.font = font;
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
        cachedSize = null;
        minSize.setWidth(width);
        minSize.setHeight(height);
        return this;
    }

    @Override
    @SuppressWarnings("deprecated")
    public Size size(UserState userState) {
        if (cachedSize != null) {
            return cachedSize.copy();
        }
        if (fontMetrics == null) {
            fontMetrics = fontMetricsCache.computeIfAbsent(font, f -> Toolkit.getDefaultToolkit().getFontMetrics(f));
        }
        Size size = new Size(0, 0);
        split(text, s -> {
            size.setWidth(Math.max(size.getWidth(), fontMetrics.stringWidth(s)));
            size.setHeight(size.getHeight() + fontMetrics.getHeight());
        });
        size.merge(minSize).add(padding);
        cachedSize = size;
        return cachedSize.copy();
    }

    @Override
    public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
        g.setColor(color);
        g.setFont(font);
        Point current = point.copy();
        split(text, s -> {
            current.addY(g.getFontMetrics().getHeight());
            g.drawString(s, current.getX() + offset.getLeft() + padding.getLeft(), current.getY() + offset.getTop() + padding.getTop());
        });
        debugDraw(g, drawState, point.add(offset).add(padding));
        point.add(drawState.getSizeMap().get(this));
    }

    private void split(String s, Consumer<String> stringConsumer) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\n') {
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
