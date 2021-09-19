package de.jxui.parser;

import de.jxui.JxUIFile;
import de.jxui.other.ComponentInternals;
import de.jxui.components.Component;
import de.jxui.components.*;
import de.jxui.components.compounds.Centered;
import de.jxui.events.Event;
import de.jxui.utils.Point;
import de.jxui.utils.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.*;

public class JxUIParser {

    private static Map<String, ComponentData<?>> componentMap = new HashMap<>();

    private static class ComponentData<T extends Component> {

        private Class<?> component;

        public ComponentData(Class<T> component) {
            this.component = component;
        }

        private T constructInstance(Object[] objects) {
            Class<?>[] classes = Arrays.stream(objects).map(Object::getClass).map(aClass -> {
                if (aClass == Integer.class) {
                    return int.class;
                }
                if (aClass == Long.class) {
                    return long.class;
                }
                return aClass;
            }).toArray(Class<?>[]::new);
            try {
                return (T) component.getConstructor(classes).newInstance(objects);
            } catch (NoSuchMethodException e) {
                throw new SecurityException("Unknown constructor");
            } catch (Exception e) {
                throw new SecurityException(e.getMessage(), e);
            }
        }

        private void callFunction(Component object, String name, Object[] objects) {
            Class<?>[] classes = Arrays.stream(objects).map(Object::getClass).map(aClass -> {
                if (aClass == Integer.class) {
                    return int.class;
                }
                if (aClass == Long.class) {
                    return long.class;
                }
                return aClass;
            }).toArray(Class<?>[]::new);
            try {
                Method method = getMethod(component, name, classes);
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new SecurityException("Unknown method '" + name + "'");
                }
                if (!Modifier.isPublic(method.getModifiers())) {
                    throw new SecurityException("Unknown method '" + name + "'");
                }
                method.invoke(object, objects);
            } catch (Exception e) {
                throw new SecurityException(e.getMessage(), e);
            }
        }

        private static Method getMethod(Class<?> current, String name, Class<?>[] classes) {
            if (current == null || current == Object.class) {
                throw new SecurityException("Unknown method '" + name + "'");
            }
            try {
                Method method = current.getDeclaredMethod(name, classes);
                if (method.getAnnotation(ComponentInternals.class) != null) {
                    throw new SecurityException("Unknown method '" + name + "'");
                }
                return method;
            } catch (NoSuchMethodException e) {
                return getMethod(current.getSuperclass(), name, classes);
            }
        }
    }

    private static class MetaComponent implements Component {

        private Component component = null;

        @Override
        public void cleanUp() {
            if (component != null) component.cleanUp();
        }

        @Override
        public Size size(UserState userState) {
            if (component != null) return component.size(userState);
            return new Size(0, 0);
        }

        @Override
        public void size(Size size, UserState userState, DrawState drawState) {
            if (component != null) component.size(size, userState, drawState);
        }

        @Override
        public int spacers(UserState userState, Orientation orientation) {
            if (component != null) return component.spacers(userState, orientation);
            return 0;
        }

        @Override
        public void event(UserState userState, DrawState drawState, Point point, Event event) {
            if (component != null) component.event(userState, drawState, point, event);
        }

        @Override
        public void draw(Graphics2D g, UserState userState, DrawState drawState, Point point) {
            if (component != null) component.draw(g, userState, drawState, point);
        }
    }

    public static <T extends Component> void addComponent(Class<T> component) {
        componentMap.computeIfAbsent(component.getSimpleName(), s -> new ComponentData<>(component));
    }

    public static void main(String[] args) {
        addComponent(Text.class);
        addComponent(TextTemplate.class);
        addComponent(Spacer.class);

        JxUIFile jxUIFile = new JxUIFile(new File("./src/main/resources/Test.jxui"));
        jxUIFile.setDebug(true);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                jxUIFile.draw(this);
            }
        };
        jxUIFile.addListenersTo(canvas);
        canvas.setSize(4 * 100, 3 * 100);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private Deque<Component> components = new ArrayDeque<>();

    @Getter
    private Component component;

    private Component lastComponent = null;

    public JxUIParser(InputStream inputStream) {
        new BufferedReader(new InputStreamReader(inputStream)).lines().map(String::trim).filter(s -> !s.isEmpty()).filter(s -> !s.startsWith("#")).filter(s -> !s.startsWith("//")).forEach(s -> {
            if (s.equals("}")) {
                if (components.size() == 1) {
                    component = components.peek();
                }
                if (components.pop() instanceof MetaComponent) {
                    if (components.size() == 1) {
                        component = components.peek();
                    }
                    components.pop();
                }
                return;
            }
            if (s.matches("VStack *\\{")) {
                components.push(add(new VStack()));
                return;
            }
            if (s.matches("HStack *\\{")) {
                components.push(add(new HStack()));
                return;
            }
            if (s.matches("ZStack *\\{")) {
                components.push(add(new ZStack()));
                return;
            }
            if (s.matches("Centered *\\{")) {
                MetaComponent metaComponent = new MetaComponent();
                components.push(add(new Centered(metaComponent)));
                components.push(metaComponent);
                return;
            }
            if (components.isEmpty()) {
                throw new SecurityException("A .jxUI file needs to start with an Centered, VStack, HStack or ZStack");
            }
            int index = s.indexOf('(');
            if (s.startsWith(".")) {
                String methodName = s.substring(1, index);
                s = s.substring(index);
                if (s.isEmpty()) {
                    throw new SecurityException("Unrecognized line '" + s + "'");
                }

                ComponentData<?> componentData = componentMap.get(lastComponent.getClass().getSimpleName());
                if (s.equals("()")) {
                    componentData.callFunction(lastComponent, methodName, new Object[0]);
                    return;
                }

                s = s.substring(1, s.length() - 1);

                Object[] objects = parse(s);
                componentData.callFunction(lastComponent, methodName, objects);
            } else if (index >= 0) {
                ComponentData<?> componentData = componentMap.get(s.substring(0, index));
                if (componentData == null) {
                    throw new SecurityException("Unsupported type '" + s + "', please use #addComponent(Class) to add new component types");
                }
                s = s.substring(index);
                if (s.isEmpty()) {
                    throw new SecurityException("Unrecognized line '" + s + "'");
                }

                if (s.equals("()")) {
                    lastComponent = add(componentData.constructInstance(new Object[0]));
                    return;
                }
                s = s.substring(1, s.length() - 1);

                Object[] objects = parse(s);
                lastComponent = add(componentData.constructInstance(objects));
            } else {
                throw new SecurityException("Unrecognized line '" + s + "'");
            }
        });
    }

    private Component add(Component component) {
        Component current = components.peek();
        if (current == null) {
            return component;
        }
        if (current instanceof VStack) {
            ((VStack) current).add(component);
        } else if (current instanceof HStack) {
            ((HStack) current).add(component);
        } else if (current instanceof ZStack) {
            ((ZStack) current).add(component);
        } else if (current instanceof MetaComponent metaComponent) {
            if (metaComponent.component == null) {
                metaComponent.component = component;
            } else {
                throw new SecurityException("Component already contains inner component");
            }
        }
        else {
            throw new SecurityException("Enclosing component is not a Stack");
        }
        return component;
    }

    private Object[] parse(String s) {
        StringBuilder string = null;
        StringBuilder number = null;

        List<Object> objects = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"') {
                if (string == null) {
                    string = new StringBuilder();
                } else {
                    objects.add(string.toString());
                    string = null;
                }
                continue;
            }
            if (string != null) {
                string.append(c);
                continue;
            }

            if (c == '-' || (c >= '0' && c <= '9') || c == '.' || c == ',' || c == 'L') {
                if (c == ',' && number != null) {
                    String current = number.toString();
                    if (current.endsWith("L")) {
                        objects.add(Long.parseLong(current.substring(0, current.length() - 1)));
                    } else {
                        objects.add(Integer.parseInt(current));
                    }
                }
                if (number == null) {
                    number = new StringBuilder();
                }
                number.append(c);
                continue;
            }
        }
        if (number != null) {
            String current = number.toString();
            if (current.endsWith("L")) {
                objects.add(Long.parseLong(current.substring(0, current.length() - 1)));
            } else {
                objects.add(Integer.parseInt(current));
            }
        }

        return objects.toArray(new Object[0]);
    }
}
