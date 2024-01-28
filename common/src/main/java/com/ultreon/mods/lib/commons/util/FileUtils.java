package com.ultreon.mods.lib.commons.util;

import com.ultreon.mods.lib.commons.UtilityClass;

import java.io.File;
import java.io.PrintWriter;

public final class FileUtils extends UtilityClass {
    public static boolean setCwd(File dir) {
        boolean result = false; // Boolean indicating whether directory was set  

        // Desired current working directory
        File absDir = dir.getAbsoluteFile();
        if (absDir.exists() || absDir.mkdirs()) {
            result = (System.setProperty("user.dir", absDir.getAbsolutePath()) != null);
        }

        return result;
    }

    public static PrintWriter openOutputFile(String fileName) {
        PrintWriter output = null;

        try {
            output = new PrintWriter(new File(fileName).getAbsoluteFile());
        } catch (Exception ignored) {
        }

        return output;
    }

    public static String getExtension(File file) {
        String[] split = file.getName().split("\\.", 2);
        if (split.length <= 1) {
            return null;
        }

        return "." + split[1];
    }
}