package com.ultreon.mods.lib.commons.util;

import com.ultreon.mods.lib.commons.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils extends UtilityClass {
    @Deprecated
    public static RuntimeException utilityClass() {
        return new UnsupportedOperationException("Can't instantiate utility class.");
    }

    public static String getStackTrace() {
        return getStackTrace(new RuntimeException());
    }

    public static String getStackTrace(String message) {
        return getStackTrace(new RuntimeException(message));
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        String stackTrace = writer.toString();
        printWriter.close();
        return stackTrace;
    }
}
