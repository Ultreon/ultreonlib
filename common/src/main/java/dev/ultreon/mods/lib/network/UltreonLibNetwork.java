package dev.ultreon.mods.lib.network;

import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.network.api.Network;
import dev.ultreon.mods.lib.network.api.PacketRegisterContext;

public class UltreonLibNetwork extends Network {
    private static Network instance;

    private UltreonLibNetwork() {
        super(UltreonLib.MOD_ID, "network");
    }

    public static Network get() {
        return instance;
    }

    static void create() {
        instance = new UltreonLibNetwork();
    }

    @Override
    protected void registerPackets(PacketRegisterContext ctx) {

    }
}
