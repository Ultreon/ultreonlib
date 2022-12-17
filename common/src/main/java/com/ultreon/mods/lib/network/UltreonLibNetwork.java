package com.ultreon.mods.lib.network;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.network.api.Network;
import com.ultreon.mods.lib.network.api.PacketRegisterContext;
import com.ultreon.mods.lib.network.packet.ActionMenuTransferPacket;

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
        ctx.register(ActionMenuTransferPacket::new);
    }
}
