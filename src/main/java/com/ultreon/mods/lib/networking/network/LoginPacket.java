package com.ultreon.mods.lib.networking.network;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

sealed class LoginPacket implements IntSupplier {
    private int loginIndex;

    public int getLoginIndex() {
        return loginIndex;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    @Override
    public int getAsInt() {
        return loginIndex;
    }

    public static non-sealed class Reply extends LoginPacket {
        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
        }
    }
}
