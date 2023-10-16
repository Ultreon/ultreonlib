package com.ultreon.mods.lib.network.packet;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.actionmenu.ActionMenuItem;
import com.ultreon.mods.lib.actionmenu.ServerActionMenuItem;
import com.ultreon.mods.lib.network.api.packet.PacketToServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ActionMenuTransferPacket extends PacketToServer<ActionMenuTransferPacket> {
    private final int id;

    public ActionMenuTransferPacket(FriendlyByteBuf buffer) {
        this.id = buffer.readInt();
    }

    public ActionMenuTransferPacket(int id) {
        this.id = id;
    }

    @Override
    @SuppressWarnings("PlaceholderCountMatchesArgumentCount")
    protected void handle(@NotNull ServerPlayer sender) {
        UltreonLib.LOGGER.debug("Received action menu transfer packet for ID: " + id);
        ActionMenuItem action = ActionMenuItem.fromServerId(this.id);
        if (sender.hasPermissions(4)) {
            if (action instanceof ServerActionMenuItem serverAction) {
                if (id != action.serverId()) {
                    throw new IllegalArgumentException("Expected menu id to be " + id + " on server.");
                }
                serverAction.onActivate(sender);
                return;
            }
            throw new InternalError("Expected to be a server action menu item, got an impossible client side only.");
        } else {
            sender.displayClientMessage(Component.translatable("gui.ultreonlib.action_menu.access_denied"), true);
            UltreonLib.LOGGER.warn("Player %s tried to get unauthorized access to action menu '%s'.", sender.getName().getString(), action.location());
        }
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
    }
}
