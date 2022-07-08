package com.ultreon.modlib.silentlib.network.internal;

import com.ultreon.modlib.UltreonModLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;

public class UltreonModLibNetwork {
    private static final ResourceLocation NAME = new ResourceLocation(UltreonModLib.MOD_ID, "network");
    private static final int VERSION = 1;

    public static SimpleChannel channel;

    static {
        channel = NetworkRegistry.ChannelBuilder.named(NAME)
                .clientAcceptedVersions(s -> Objects.equals(s, String.valueOf(VERSION)))
                .serverAcceptedVersions(s -> Objects.equals(s, String.valueOf(VERSION)))
                .networkProtocolVersion(() -> String.valueOf(VERSION))
                .simpleChannel();

        channel.messageBuilder(LeftClickItemPacket.class, 1)
                .decoder(LeftClickItemPacket::fromBytes)
                .encoder(LeftClickItemPacket::toBytes)
                .consumer(LeftClickItemPacket::handle)
                .add();
        channel.messageBuilder(DisplayNBTPacket.class, 2)
                .decoder(DisplayNBTPacket::fromBytes)
                .encoder(DisplayNBTPacket::toBytes)
                .consumer(DisplayNBTPacket::handle)
                .add();
        channel.messageBuilder(SpawnEntityPacket.class, 3)
                .decoder(SpawnEntityPacket::decode)
                .encoder(SpawnEntityPacket::encode)
                .consumer(SpawnEntityPacket::handle)
                .add();
    }

    public static void init() {
    }
}
