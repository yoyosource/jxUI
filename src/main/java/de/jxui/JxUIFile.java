package de.jxui;

import de.jxui.parser.JxUIParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class JxUIFile extends JxUI {

    private File file;

    public JxUIFile(File file) {
        if (!file.isFile()) {
            throw new SecurityException("File is not a file");
        }
        if (!file.exists()) {
            throw new SecurityException("File does not exist");
        }
        this.file = file;
        Thread thread = new Thread(() -> {
            long lastModified = file.lastModified();
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (lastModified < file.lastModified()) {
                    parse();
                    lastModified = file.lastModified();
                }
            }
        });
        thread.setName("JxUIFile Updater for '" + file.getAbsolutePath() + "'");
        thread.setDaemon(true);
        thread.start();

        parse();
    }

    private void parse() {
        try {
            this.component = new JxUIParser(new BufferedInputStream(new FileInputStream(file))).getComponent();
            repainter.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
