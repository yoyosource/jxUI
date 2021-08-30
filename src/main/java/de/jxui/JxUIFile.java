package de.jxui;

import de.jxui.components.Component;

import java.io.File;

public class JxUIFile {

    private File file;
    private long lastModified;

    private Component oldComponent = null;
    private Component component = null;

    public JxUIFile(File file) {
        if (!file.isFile()) {
            throw new SecurityException("File is not a file");
        }
        if (!file.exists()) {
            throw new SecurityException("File does not exist");
        }
        this.file = file;
        lastModified = file.lastModified();

        parse();
    }

    private void parse() {
        oldComponent = component;
        component = null;
    }
}
