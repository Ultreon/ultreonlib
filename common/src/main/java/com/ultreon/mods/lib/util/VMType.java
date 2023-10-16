package com.ultreon.mods.lib.util;

import com.google.common.annotations.Beta;
import org.jetbrains.annotations.Nullable;
import oshi.SystemInfo;

/**
 * @deprecated unused, and just a random dumb class.
 */
@Deprecated(forRemoval = true)
public enum VMType {
    @Deprecated
    VMWARE("VMware, Inc.", "VMware");

    private final String manufacturer;
    private final String name;

    VMType(String manufacturer, String name) {
        this.manufacturer = manufacturer;
        this.name = name;
    }

    @Nullable
    @Deprecated
    public static VMType fromManufacturer(String manufacturer) {
        for (VMType vmType : values()) {
            if (vmType.manufacturer.equals(manufacturer)) {
                return vmType;
            }
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static VMType system() {
        SystemInfo systemInfo = new SystemInfo();
        return fromManufacturer(systemInfo.getHardware().getComputerSystem().getManufacturer());
    }

    @Deprecated
    public String getName() {
        return this.name;
    }
}
