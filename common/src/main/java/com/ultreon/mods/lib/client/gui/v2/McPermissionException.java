package com.ultreon.mods.lib.client.gui.v2;

public class McPermissionException extends McOperatingSystemException {
    private final McPermission permission;

    public McPermissionException(String message, McPermission permission) {
        super(message);
        this.permission = permission;
    }

    public McPermissionException(String message, Throwable cause, McPermission permission) {
        super(message, cause);
        this.permission = permission;
    }

    public McPermission getPermission() {
        return this.permission;
    }
}
