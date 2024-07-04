package dev.ultreon.mods.lib.network.api;

import com.google.common.base.CaseFormat;
import dev.architectury.networking.NetworkManager;
import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.network.api.packet.BasePacket;
import dev.ultreon.mods.lib.network.api.packet.ClientEndpoint;
import dev.ultreon.mods.lib.network.api.packet.ServerEndpoint;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class PacketRegisterContext {
    private final Network network;
    private final ResourceLocation name;
    private int id;

    PacketRegisterContext(Network network, ResourceLocation name, int id) {
        this.network = network;
        this.name = name;
        this.id = id;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    @Deprecated(forRemoval = true)
    public final <T extends BasePacket<T>> int register(Function<FriendlyByteBuf, T> construct, T... type) {
        final int id = this.id++;
        final Constructor<T> declaredConstructor;

        Class<T> clazz = (Class<T>) type.getClass().getComponentType();

        try {
            declaredConstructor = clazz.getDeclaredConstructor(FriendlyByteBuf.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Packet " + construct.getClass().getName() + " is missing a constructor that takes a FriendlyByteBuf as an argument.", e);
        }

        PacketInfo annotation = clazz.getAnnotation(PacketInfo.class);

        if (!declaredConstructor.canAccess(null)) {
            try {
                declaredConstructor.setAccessible(true);
            } catch (SecurityException e) {
                throw new RuntimeException("Can't access constructor of " + construct.getClass().getName() + ".", e);
            }
        }

        String s = name.getPath() + "/dynamic/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getName());
        if (annotation != null) s = name.getPath() + "/" + annotation.name();
        UltreonLib.LOGGER.info("Registering packet: " + s);

        CustomPacketPayload.Type<T> id1 = new CustomPacketPayload.Type<>(ResourceLocation.tryBuild(name.getNamespace(), s));
        if (ClientEndpoint.class.isAssignableFrom(clazz)) {
            NetworkManager.registerReceiver(
                    NetworkManager.Side.S2C,
                    id1,
                    StreamCodec.<FriendlyByteBuf, T>ofMember(T::toBytes, construct::apply),
                    (value, context) -> value.handlePacket(() -> context)
            );

        }

        if (ServerEndpoint.class.isAssignableFrom(clazz)) {
            NetworkManager.registerReceiver(
                    NetworkManager.Side.C2S,
                    id1,
                    StreamCodec.<FriendlyByteBuf, T>ofMember(T::toBytes, construct::apply),
                    (value, context) -> value.handlePacket(() -> context)
            );
        }
        network.payloads.put(clazz, id1);

        return id;
    }


    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final <T extends BasePacket<T>> int add(T... type) {
        final int id = this.id++;
        final Constructor<T> declaredConstructor;

        Class<T> clazz = (Class<T>) type.getClass().getComponentType();

        try {
            declaredConstructor = clazz.getDeclaredConstructor(FriendlyByteBuf.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Packet " + clazz.getName() + " is missing a constructor that takes a FriendlyByteBuf as an argument.", e);
        }

        PacketInfo annotation = clazz.getAnnotation(PacketInfo.class);

        if (!declaredConstructor.canAccess(null)) {
            try {
                declaredConstructor.setAccessible(true);
            } catch (SecurityException e) {
                throw new RuntimeException("Can't access constructor of " + clazz.getName() + ".", e);
            }
        }

        String s = name.getPath() + "/dynamic/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getName());
        if (annotation != null) s = name.getPath() + "/" + annotation.name();
        UltreonLib.LOGGER.info("Registering packet: " + s);

        CustomPacketPayload.Type<T> id1 = new CustomPacketPayload.Type<>(ResourceLocation.tryBuild(name.getNamespace(), s));
        if (ClientEndpoint.class.isAssignableFrom(clazz)) {
            NetworkManager.registerReceiver(
                    NetworkManager.Side.S2C,
                    id1,
                    StreamCodec.<FriendlyByteBuf, T>ofMember(T::toBytes, object -> {
                        try {
                            return declaredConstructor.newInstance(object);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }),
                    (value, context) -> value.handlePacket(() -> context)
            );

        }

        if (ServerEndpoint.class.isAssignableFrom(clazz)) {
            NetworkManager.registerReceiver(
                    NetworkManager.Side.C2S,
                    id1,
                    StreamCodec.<FriendlyByteBuf, T>ofMember(T::toBytes, object -> {
                        try {
                            return declaredConstructor.newInstance(object);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }),
                    (value, context) -> value.handlePacket(() -> context)
            );
        }
        network.payloads.put(clazz, id1);

        return id;
    }

}
