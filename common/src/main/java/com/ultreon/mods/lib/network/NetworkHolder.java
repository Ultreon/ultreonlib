package com.ultreon.mods.lib.network;

/**
 * Holder for injecting the network class into a field.
 */
public @interface NetworkHolder {
    /**
     * The mod-id of the mod that this network is for.
     *
     * @return the mod-id.
     */
    String value();
}
