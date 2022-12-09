package com.ultreon.mods.lib.network;

import com.ultreon.mods.lib.network.packet.BasePacket;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public abstract class Network {
    private final String modId;
    private final String channelName;

    public Connection getConnection() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection();
    }

    private NetworkChannel channel;

    protected Network(String modId, String channelName) {
        this.modId = modId;
        this.channelName = channelName;

        NetworkManager.registerNetwork(this);
    }

    @Deprecated
    protected Network(String modId, String channelName, @Deprecated int ignoredVersion) {
        this(modId, channelName);
    }

    public final void init() {
        int id = 0;
        channel = NetworkChannel.create(new ResourceLocation(modId(), channelName()));

        registerPackets(new PacketRegisterContext(channel, id));
    }

    protected abstract void registerPackets(PacketRegisterContext ctx);

    public final String channelName() {
        return channelName;
    }

    public final String modId() {
        return modId;
    }

    @Environment(EnvType.CLIENT)
    public <T extends BasePacket<T>> void sendToServer(T message) {
        if (Minecraft.getInstance().getConnection() != null) {
            channel.sendToServer(message);
        } else {
            Minecraft.getInstance().doRunTask(() ->
                    message.handlePacket(this::createServerPacket));
        }
    }

    public <T extends BasePacket<T>> void sendToClient(BasePacket<T> messageNotification, Player player) { // has to be ServerPlayer if world is not null
        if (player == null) {
            messageNotification.handlePacket(() -> new PacketContext() {
                @Override
                public Player getPlayer() {
                    return player;
                }

                @Override
                public void queue(Runnable runnable) {

                }

                @Override
                public Env getEnvironment() {
                    return Env.CLIENT;
                }
            });
            return;
        }
        channel.sendToPlayer((ServerPlayer) player, messageNotification);
    }

    public final ResourceLocation getId() {
        return new ResourceLocation(modId(), channelName());
    }

    private PacketContext createServerPacket() {
        return new PacketContext() {

            @Override
            public Player getPlayer() {
                return Minecraft.getInstance().player;
            }

            @Override
            public void queue(Runnable runnable) {

            }

            @Override
            public Env getEnvironment() {
                return Env.SERVER;
            }
        };
    }
}
