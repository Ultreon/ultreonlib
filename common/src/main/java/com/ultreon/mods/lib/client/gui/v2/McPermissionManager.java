package com.ultreon.mods.lib.client.gui.v2;

import java.util.HashMap;
import java.util.Map;

public class McPermissionManager {
    private final Map<ApplicationId, McPermissionContainer> permissions = new HashMap<>();

    public McPermissionManager() {

    }

    void grantPermission(ApplicationId applicationId, McPermission permission) {
        this.permissions.get(applicationId).grant(permission);
    }

    void onAppDestroyed(McApplication application) {

    }

    void onAppSpawned(McApplication application) {

    }

    void registerPerms(ApplicationId id) {
        this.permissions.putIfAbsent(id, new McPermissionContainer());
    }

    public boolean hasPermission(McApplication executor, McPermission permission) {
        if (permission == null) return false;
        if (executor instanceof McKernel) return true;
        return this.permissions.get(executor.getId()).has(permission);
    }

    public void dispose() {
        this.permissions.values().forEach(McPermissionContainer::dispose);
        this.permissions.clear();
    }

    public void checkPermission(McApplication executor, McPermission permission) throws AccessDeniedException {
        if (permission != null && this.hasPermission(executor, permission)) return;

        throw new AccessDeniedException(executor, permission);
    }
}
