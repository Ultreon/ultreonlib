package com.ultreon.mods.lib.client.gui.v2;

public final class McDesktopApplication extends McApplication {
    private McDesktopWindow desktop;
    private McTaskbarWindow taskbar;

    public McDesktopApplication() {
        super(id());
    }

    public static ApplicationId id() {
        return new ApplicationId("com.ultreon.app:mc-desktop");
    }

    @Override
    public void create() {
        super.create();

        this.desktop = this.createDesktop();
        this.desktop.create();
        this.desktop.setBottomMost(true);

        this.taskbar = this.createTaskbar();
        this.taskbar.create();
        this.taskbar.setTopMost(true);
    }

    @Override
    public void update() {
        if (!this.taskbar.isValid()) {
            this.taskbar = this.createTaskbar();
            this.taskbar.create();
            this.taskbar.setTopMost(true);
        }
        if (!this.desktop.isValid()) {
            this.desktop = this.createDesktop();
            this.desktop.create();
            this.desktop.setBottomMost(true);
        }
    }

    private McDesktopWindow createDesktop() {
        return new McDesktopWindow(this);
    }

    private McTaskbarWindow createTaskbar() {
        return new McTaskbarWindow(this, 20);
    }
    
    public McDesktopWindow getDesktop() {
        return desktop;
    }

    public McTaskbarWindow getTaskbar() {
        return taskbar;
    }
}
