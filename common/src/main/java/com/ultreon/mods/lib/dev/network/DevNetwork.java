package com.ultreon.mods.lib.dev.network;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.network.api.Network;
import com.ultreon.mods.lib.network.api.PacketRegisterContext;

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
        ctx.register(TestBiDirectionalPacket::new);
        ctx.register(TestToClientPacket::new);
    }
}
