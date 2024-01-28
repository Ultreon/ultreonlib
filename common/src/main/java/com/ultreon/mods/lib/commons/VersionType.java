package com.ultreon.mods.lib.commons;

public enum VersionType {
    ALPHA("alpha"), BETA("beta"), RELEASE("release"), CANDIDATE("rc");

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
