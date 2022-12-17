package com.ultreon.mods.lib.util;

import com.google.common.annotations.Beta;
import org.jetbrains.annotations.Nullable;

@Beta
public enum VMType {
    VMWARE("VMware, Inc.", "VMware");

    private final String manufacturer;
    private final String name;

    VMType(String manufacturer, String name) {
        this.manufacturer = manufacturer;
        this.name = name;
    }

    @Nullable
    public static VMType getFromManufacturer(String manufacturer) {
        for (VMType vmType : values()) {
            if (vmType.manufacturer.equals(manufacturer)) {
                return vmType;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
