package com.ultreon.mods.lib.client.gui.v2;

public class AppNotFoundException extends McOperatingSystemException {
    public AppNotFoundException(ApplicationId id) {
        super("Application not found: " + id);
    }
}
