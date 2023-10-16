package com.ultreon.mods.lib.client.gui.v2;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class McApplication {
    final Set<McWindow> windows = new CopyOnWriteArraySet<>();
    private final ApplicationId id;
    private McOperatingSystem system;
    private String[] argv;
    private long pid;
    McWindowManager wm;

    public McApplication(String id) {
        this(new ApplicationId(id));
    }

    public McApplication(String group, String module) {
        this(new ApplicationId(group, module));
    }

    public McApplication(ApplicationId id) {
        this.id = id;
    }

    public ApplicationId getId() {
        return id;
    }

    public void create() {

    }

    void _main(McOperatingSystem system, McWindowManager wm, String[] argv, long pid) {
        this.system = system;
        this.wm = wm;
        this.argv = argv;
        this.create();
        this.pid = pid;
    }

     public void createWindow(McWindow window) {
        this.wm.createWindow(window);
        this.windows.add(window);
    }

    public long getPid() {
        return pid;
    }

    protected final String[] getArgv() {
        return Arrays.copyOf(this.argv, this.argv.length);
    }

    public IMcOperatingSystem getSystem() {
        return system;
    }

    @CanIgnoreReturnValue
    public boolean spawnApplication(ApplicationId id, String... argv) throws AppNotFoundException, McPermissionException {
        return this.system.spawn(this, id, argv);
    }

    public final void quit() {
        this.windows.forEach(McWindow::close);
        this.dispose();
    }

    protected void dispose() {

    }

    public void _destroyWindow(McWindow window) {
        this.windows.remove(window);
    }

    public void update() {

    }
}
