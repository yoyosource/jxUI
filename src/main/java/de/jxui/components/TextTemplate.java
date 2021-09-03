package de.jxui.components;

import de.jxui.utils.Size;
import de.jxui.utils.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;

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
                                current.append(userState.get(s));
                                break;
                            }
                        }
                    } else {
                        current.append(userState.get(token.text));
                    }
                } else {
                    current.append(token.text);
                }
            }
            return current.toString();
        };
    }

    @Override
    public Size size(UserState userState) {
        super.text = textGenerator.apply(userState);
        return super.size(userState);
    }
}
