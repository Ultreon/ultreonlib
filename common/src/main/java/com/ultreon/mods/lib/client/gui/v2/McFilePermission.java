package com.ultreon.mods.lib.client.gui.v2;

import java.util.Objects;

public class McFilePermission extends McPermission {
    private final String path;

    public McFilePermission(String name, String path) {
        super(name);
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return "McFilePermission{" +
                "name='" + getName() + "', " +
                "path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        McFilePermission that = (McFilePermission) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), path);
    }
}
