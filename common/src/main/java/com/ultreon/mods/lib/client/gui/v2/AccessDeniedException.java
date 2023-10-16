package com.ultreon.mods.lib.client.gui.v2;

public class AccessDeniedException extends McPermissionException {

    public AccessDeniedException(McApplication executor, McPermission permission) {
        super("App " + executor.getId() + " has no permission: " + permission, permission);
    }

    public AccessDeniedException(McApplication executor, McPermission permission, Throwable cause) {
        super("App " + executor.getId() + " has no permission: " + permission, cause, permission);
    }
}
