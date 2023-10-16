package com.ultreon.mods.lib.client.gui.v2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class McPermissionContainer {
    private final Set<McPermission> permissions = new HashSet<>();

    McPermissionContainer() {

    }

    void dispose() {
        this.permissions.clear();
    }

    void grant(McPermission permission) {
        this.permissions.add(permission);
    }

    void revoke(McPermission permission) {
        this.permissions.add(permission);
    }

    public boolean has(McPermission permission) {
        return this.permissions.contains(permission);
    }
}
