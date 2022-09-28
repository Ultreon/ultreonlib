package com.ultreon.mods.lib.core.network;

import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.networking.network.Network;
import com.ultreon.mods.lib.networking.network.PacketRegisterContext;

public class ModdingLibraryNet extends Network {
    private static final int VERSION = 1;

    public ModdingLibraryNet() {
        super(ModdingLibrary.MOD_ID, "net", VERSION);
    }

    @Override
    protected void registerPackets(PacketRegisterContext packetRegisterContext) {
        packetRegisterContext.register(ShowNbtPacket.class, ShowNbtPacket::new);
    }
}
