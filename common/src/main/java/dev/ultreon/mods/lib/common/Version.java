package dev.ultreon.mods.lib.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Deprecated(forRemoval = true)
public record Version(int major, int minor, int build, VersionType type, int release) implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.build + "-" + this.type.getName() + this.release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return this.major == version.major &&
               this.minor == version.minor &&
               this.release == version.release &&
               this.type == version.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.major, this.minor, this.type, this.release);
    }

}
