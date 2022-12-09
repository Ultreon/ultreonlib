package com.ultreon.mods.lib.core.network;

import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.networking.network.Network;
import com.ultreon.mods.lib.networking.network.PacketRegisterContext;

public class ModdingLibraryNet extends Network {
    private static final int VERSION = 1;
    static ModdingLibraryNet instance;

    ModdingLibraryNet() {
        super(ModdingLibrary.MOD_ID, "net", VERSION);
    }

    public static ModdingLibraryNet get() {
        return instance;
    }

    @Override
    protected void registerPackets(PacketRegisterContext ctx) {
        ctx.register(ShowNbtPacket.class, ShowNbtPacket::new);
        ctx.register(PipelinePacket.class, PipelinePacket::new);
    }
}
