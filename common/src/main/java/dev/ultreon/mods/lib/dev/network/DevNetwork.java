package dev.ultreon.mods.lib.dev.network;

import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.network.api.Network;
import dev.ultreon.mods.lib.network.api.PacketRegisterContext;

public class DevNetwork extends Network {
    private static Network instance;

    public DevNetwork() {
        super(UltreonLib.MOD_ID, "dev");
        instance = this;
    }

    public static Network get() {
        return instance;
    }

    @Override
    protected void registerPackets(PacketRegisterContext ctx) {
        ctx.<TestBiDirectionalPacket>add();
        ctx.<TestToClientPacket>add();
    }
}
