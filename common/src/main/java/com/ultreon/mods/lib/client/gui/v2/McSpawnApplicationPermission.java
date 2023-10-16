package com.ultreon.mods.lib.client.gui.v2;

import java.util.Objects;

public class McSpawnApplicationPermission extends McPermission {
    private final ApplicationId id;

    public McSpawnApplicationPermission(McDesktopApplication application) {
        this(application.getId());
    }

    public McSpawnApplicationPermission(ApplicationId id) {
        super("spawn_application");
        this.id = id;
    }

    @Override
    public String toString() {
        return "McSpawnApplicationPermission{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        McSpawnApplicationPermission that = (McSpawnApplicationPermission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
