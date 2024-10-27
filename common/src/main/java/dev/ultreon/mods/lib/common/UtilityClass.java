package dev.ultreon.mods.lib.common;

import dev.ultreon.mods.lib.common.util.ExceptionUtils;

@Deprecated(forRemoval = true)
public class UtilityClass {
    protected UtilityClass() {
        throw ExceptionUtils.utilityClass();
    }
}
