package de.jxui.utils;

import lombok.Setter;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserState {

    private interface Ignored {
        void put(Object key, Object value);
        void remove(Object key);
        Object get(Object key);
        Object getOrDefault(Object key, Object defaultValue);
    }

    @Delegate(excludes = Ignored.class)
    private Map<Object, Object> state = new HashMap<>();

    private String operatingSystem = null;
    private boolean windows = false;
    private boolean mac = false;
    private boolean linux = false;

    @Setter
    private Size size;

    @Setter
    private Runnable changeRunnable;

    public UserState() {
    }

    public boolean windows() {
        if (operatingSystem == null) getOperatingSystem();
        return windows;
    }

    public boolean mac() {
        if (operatingSystem == null) getOperatingSystem();
        return mac;
    }

    public boolean linux() {
        if (operatingSystem == null) getOperatingSystem();
        return linux;
    }

    public String getOperatingSystem() {
        if (operatingSystem == null) {
            operatingSystem = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (operatingSystem.contains("mac") || operatingSystem.contains("darwin")) {
                mac = true;
            } else if (operatingSystem.contains("windows")) {
                windows = true;
            } else if (operatingSystem.contains("nux")) {
                linux = true;
            }
        }
        return operatingSystem;
    }

    public int getCanvasWidth() {
        return size.getWidth();
    }

    public int getCanvasHeight() {
        return size.getHeight();
    }

    public void put(Object key, Object value) {
        Object currentValue = get(key);
        if (currentValue == null && value == null) {
            return;
        }
        state.put(key, value);
        changeRunnable.run();
    }

    public void remove(Object key) {
        state.remove(key);
        changeRunnable.run();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        return (T) state.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(Object key, T defaultValue) {
        return (T) state.getOrDefault(key, defaultValue);
    }

    public String toString() {
        return "State{" + state + "}";
    }
}
