package com.ultreon.mods.lib.network.packet;

import com.ultreon.mods.lib.actionmenu.ActionMenuScreen;
import com.ultreon.mods.lib.network.UltreonLibNetwork;
import com.ultreon.mods.lib.network.api.packet.PacketToClient;
import com.ultreon.mods.lib.network.api.packet.PacketToServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class AMenuItemPermissionRequestPacket extends PacketToServer<AMenuItemPermissionRequestPacket> {
    public AMenuItemPermissionRequestPacket(FriendlyByteBuf buffer) {

    }

    public AMenuItemPermissionRequestPacket() {

    }

    public void handle(ServerPlayer player) {
        if (player.hasPermissions(4)) {
            UltreonLibNetwork.get().sendToClient(new Reply(true), player);
        } else {
            UltreonLibNetwork.get().sendToClient(new Reply(false), player);
        }
    }

    @SuppressWarnings("unused")
    public void toBytes(FriendlyByteBuf buffer) {

    }

    public static class Reply extends PacketToClient<Reply> {
        private final boolean allowed;

        public Reply(FriendlyByteBuf buffer) {
            this.allowed = buffer.readBoolean();
        }

        public Reply(boolean allowed) {
            this.allowed = allowed;
        }

        public void toBytes(FriendlyByteBuf buffer) {
            buffer.writeBoolean(allowed);
        }

        public boolean isAllowed() {
            return allowed;
        }

        @Override
        @Environment(EnvType.CLIENT)
        protected void handle() {
            Screen screen = Minecraft.getInstance().screen;
            if (screen instanceof ActionMenuScreen menuScreen) {
                menuScreen.handlePermission(this);
            }
        }
    }
}
