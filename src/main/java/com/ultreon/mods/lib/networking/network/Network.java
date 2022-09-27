package com.ultreon.mods.lib.networking.network;

import com.ultreon.mods.lib.networking.NetworkLib;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;

public abstract class Network {
    private final String modId;
    private final String channelName;
    private final String version;

    public Connection getManager() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection();
    }

    private SimpleChannel channel;

    protected Network(String modId, String channelName, int version) {
        this.modId = modId;
        this.channelName = channelName;
        this.version = modId + "-net" + version;

        NetworkLib.registerNetwork(this);
    }

    public final void init() {
        int id = 0;
        channel = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(modId(), channelName()))
                .clientAcceptedVersions(s -> Objects.equals(s, version))
                .serverAcceptedVersions(s -> Objects.equals(s, version))
                .networkProtocolVersion(() -> version)
                .simpleChannel();

        /////////////////////////////////
        //     PACKET REGISTRATION     //
        /////////////////////////////////
        channel.messageBuilder(LoginPacket.Reply.class, id++)
                .loginIndex(LoginPacket::getLoginIndex, LoginPacket::setLoginIndex)
                .decoder(buffer -> new LoginPacket.Reply())
                .encoder((msg, buffer) -> {
                })
                .consumer(HandshakeHandler.indexFirst((hh, msg, ctx) -> msg.handle(ctx)))
                .add();

        registerPackets(new PacketRegisterContext(channel, id));
    }

    protected abstract void registerPackets(PacketRegisterContext packetRegisterContext);

    public final String channelName() {
        return channelName;
    }

    public final String modId() {
        return modId;
    }

    public final void sendToServer(BasePacket<?> packet) {
        channel.sendToServer(packet);
    }

    public final void sendToClient(BasePacket<?> packet, ServerPlayer player) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public final void sendAll(BasePacket<?> packet) {
        channel.send(PacketDistributor.ALL.with(() -> null), packet);
    }

    public final void sendToLevel(BasePacket<?> packet, ResourceKey<Level> levelKey) {
        channel.send(PacketDistributor.DIMENSION.with(() -> levelKey), packet);
    }

    public final void sendToLevel(BasePacket<?> packet, Level level) {
        channel.send(PacketDistributor.DIMENSION.with(level::dimension), packet);
    }

    public final void sendToChunk(BasePacket<?> packet, LevelChunk chunk) {
        channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
    }

    public final ResourceLocation getId() {
        return new ResourceLocation(modId(), channelName());
    }
}
