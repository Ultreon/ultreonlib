package dev.ultreon.mods.lib.common;

public enum VersionType {
    ALPHA("alpha"), BETA("beta"), CANDIDATE("rc"), RELEASE("release");

    private final String name;

    VersionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String toRepresentation() {
        return "VersionType{" +
                "name='" + this.name + '\'' +
                '}';
    }

    public String getName() {
        return this.name;
    }
}
