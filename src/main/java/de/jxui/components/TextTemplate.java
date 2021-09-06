package de.jxui.components;

import de.jxui.utils.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TextTemplate extends Text {

    private Function<UserState, String> textGenerator;

    @Data
    @AllArgsConstructor
    private class Token {
        private String text;
        private int type;
    }

    public TextTemplate(String text) {
        StringBuilder st = new StringBuilder();
        List<Token> tokens = new ArrayList<>();
        int type = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                tokens.add(new Token(st.toString(), type));
                st = new StringBuilder();
                type = 1;
                continue;
            }
            if (text.charAt(i) == '}') {
                tokens.add(new Token(st.toString(), type));
                st = new StringBuilder();
                type = 0;
                continue;
            }
            st.append(text.charAt(i));
        }
        if (st.length() > 0) {
            tokens.add(new Token(st.toString(), 0));
        }

        textGenerator = userState -> {
            StringBuilder current = new StringBuilder();
            for (Token token : tokens) {
                if (token.type == 1) {
                    if (token.text.contains("|")) {
                        String[] strings = token.text.split("\\|");
                        for (int i = 0; i < strings.length; i++) {
                            String s = strings[i];
                            if (s.startsWith("'") && s.endsWith("'")) {
                                current.append(s, 1, s.length() - 1);
                                break;
                            }
                            if (userState.containsKey(s)) {
                                current.append(userState.<Object>get(s));
                                break;
                            }
                        }
                    } else {
                        current.append(userState.<Object>get(token.text));
                    }
                } else {
                    current.append(token.text);
                }
            }
            return current.toString();
        };
    }

    @Override
    public TextTemplate padding() {
        super.padding();
        return this;
    }

    @Override
    public TextTemplate padding(@NonNull Padding padding) {
        super.padding(padding);
        return this;
    }

    @Override
    public TextTemplate padding(Direction direction, int value) {
        super.padding(direction, value);
        return this;
    }

    @Override
    public TextTemplate offset() {
        super.offset();
        return this;
    }

    @Override
    public TextTemplate offset(@NonNull Offset offset) {
        super.offset(offset);
        return this;
    }

    @Override
    public TextTemplate offset(Direction direction, int value) {
        super.offset(direction, value);
        return this;
    }

    @Override
    public TextTemplate setColor(Color color) {
        super.setColor(color);
        return this;
    }

    @Override
    public TextTemplate size(int size) {
        super.size(size);
        return this;
    }

    @Override
    public TextTemplate style(int style) {
        super.style(style);
        return this;
    }

    @Override
    public TextTemplate font(String name) {
        super.font(name);
        return this;
    }

    @Override
    public TextTemplate setFont(Font font) {
        super.setFont(font);
        return this;
    }

    @Override
    public TextTemplate color(Color color) {
        super.color(color);
        return this;
    }

    @Override
    public TextTemplate color(int r, int g, int b) {
        super.color(r, g, b);
        return this;
    }

    @Override
    public TextTemplate minSize(int width, int height) {
        super.minSize(width, height);
        return this;
    }

    @Override
    public Size size(UserState userState) {
        setText(textGenerator.apply(userState));
        return super.size(userState);
    }
}
