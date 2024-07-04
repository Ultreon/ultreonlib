package dev.ultreon.mods.lib.network.api;

import dev.architectury.networking.NetworkManager;
import dev.ultreon.mods.lib.network.api.packet.BasePacket;
import dev.ultreon.mods.lib.network.api.packet.ClientEndpoint;
import dev.ultreon.mods.lib.network.api.packet.ServerEndpoint;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import dev.ultreon.mods.lib.util.ServerLifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Network {
    private final String modId;
    private final String channelName;
    public Map<Class<?>, CustomPacketPayload.Type<? extends BasePacket<?>>> payloads = new HashMap<>();
    private boolean registered = false;

    @Deprecated(forRemoval = true)
    public Connection getConnection() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection();
    }

    protected NetworkManager channel;

    protected Network(String modId, String channelName) {
        this.modId = modId;
        this.channelName = channelName;

        NetworkSystem.registerNetwork(this);
    }

    @Deprecated
    protected Network(String modId, String channelName, @Deprecated int ignoredVersion) {
        this(modId, channelName);
    }

    final void init() {
        if (this.registered) {
            throw new IllegalArgumentException("Network " + modId + ":" + channelName + " was already registered!");
        }
        this.registered = true;
        int id = 0;

        registerPackets(new PacketRegisterContext(this, ResourceLocation.tryBuild(modId, channelName), id));
    }

    protected abstract void registerPackets(PacketRegisterContext ctx);

    public final String channelName() {
        return channelName;
    }

    public final String modId() {
        return modId;
    }

    @Environment(EnvType.CLIENT)
    public <T extends BasePacket<T> & ServerEndpoint> void sendToServer(T message) {
        if (Minecraft.getInstance().getConnection() != null) {
            NetworkManager.sendToServer(message);
        } else {
            Minecraft.getInstance().doRunTask(() ->
                    message.handlePacket(this::createServerPacket));
        }
    }

    public <T extends BasePacket<T> & ClientEndpoint> void sendToClient(BasePacket<T> messageNotification, Player player) { // has to be ServerPlayer if world is not null
        if (player == null) {
            messageNotification.handlePacket(() -> new PacketContext() {
                @Override
                public Player getPlayer() {
                    return null;
                }

                @Override
                public void queue(Runnable runnable) {

                }

                @Override
                public Env getEnvironment() {
                    return Env.CLIENT;
                }

                @Override
                public RegistryAccess registryAccess() {
                    return ServerLifecycle.getCurrentServer().registryAccess();
                }
            });
            return;
        }
        NetworkManager.sendToPlayer((ServerPlayer) player, messageNotification);
    }

    public final ResourceLocation getId() {
        return ResourceLocation.tryBuild(modId(), channelName());
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

            @Override
            public RegistryAccess registryAccess() {
                return null;
            }
        };
    }
}
