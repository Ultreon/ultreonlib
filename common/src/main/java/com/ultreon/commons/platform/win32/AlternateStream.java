package com.ultreon.commons.platform.win32;

import java.io.*;

/**
 * Alternate Data Streams
 */
public record AlternateStream(File file, String id) {
    public OutputStream openOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file.getPath() + ":" + id);
    }

    public InputStream openInputStream() throws FileNotFoundException {
        return new FileInputStream(file.getPath() + ":" + id);
    }

    public Reader openReader() throws FileNotFoundException {
        return new FileReader(file.getPath() + ":" + id);
    }

    public Writer openWriter() throws IOException {
        return new FileWriter(file.getPath() + ":" + id);
    }

    public String getPath() {
        return file.getPath() + ":" + id;
    }
}
