package com.ultreon.mods.lib.commons;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Profiler {
    private final Map<String, Long> values = new HashMap<>();

    private final Map<String, Long> start = new HashMap<>();

    public void start() {
    }

    public void startSection(String name) {
        this.start.put(name, System.currentTimeMillis());
    }

    public void endSection(String name) {
        Long start = this.start.remove(name);
        if (start == null) start = 0L;
        long end = System.currentTimeMillis();
        long time = end - start;
        this.values.put(name, time);
    }

    public Map<String, Long> end() {
        return Collections.unmodifiableMap(this.values);
    }

    public void section(String name, Runnable block) {
        this.startSection(name);
        block.run();
        this.endSection(name);
    }
}
