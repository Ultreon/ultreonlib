package com.ultreon.mods.lib.client.gui.v2;

import java.util.Objects;

public class McPermission {
    public static final McPermission FILE_SYSTEM = new McPermission("file_system");
    public static final McPermission SPAWN_APPLICATION = new McPermission("spawn_application");
    public static final McPermission HARD_ERROR = new McPermission("hard_error");
    public static final McPermission SHUTDOWN = new McPermission("shutdown");

    private final String name;

    public McPermission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "McPermission{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        McPermission that = (McPermission) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
