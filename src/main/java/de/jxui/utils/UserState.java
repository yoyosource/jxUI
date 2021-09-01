package de.jxui.utils;

import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserState {

    @Delegate
    private Map<Object, Object> state = new HashMap<>();

    private String operatingSystem = null;
    private boolean windows = false;
    private boolean mac = false;
    private boolean linux = false;

    private final Size size;

    public UserState(Size size) {
        this.size = size;
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
}
