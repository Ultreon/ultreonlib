package com.ultreon.mods.lib.networking.network;

import io.netty.handler.codec.DecoderException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.simple.SimpleChannel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class PacketRegisterContext {
    private final SimpleChannel channel;
    private int id;

    PacketRegisterContext(SimpleChannel channel, int id) {
        this.channel = channel;
        this.id = id;
    }

    public <T extends BasePacket<T>> int register(Class<T> clazz, Function<FriendlyByteBuf, T> construct) {
        final int id = this.id++;
        final Constructor<T> declaredConstructor;

        try {
            declaredConstructor = clazz.getDeclaredConstructor(FriendlyByteBuf.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Packet " + construct.getClass().getName() + " is missing a constructor that takes a FriendlyByteBuf as an argument.", e);
        }

        if (!declaredConstructor.canAccess(null)) {
            try {
                declaredConstructor.setAccessible(true);
            } catch (SecurityException e) {
                throw new RuntimeException("Can't access constructor of " + construct.getClass().getName() + ".", e);
            }
        }

        channel.messageBuilder(clazz, id)
                .decoder(buffer -> {
                    try {
                        return declaredConstructor.newInstance(buffer);
                    } catch (InstantiationException e) {
                        throw new RuntimeException("Couldn't decode packet " + construct.getClass().getName() + " because it couldn't be instantiated.", e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Couldn't decode packet " + construct.getClass().getName() + " because it couldn't be accessed.", e);
                    } catch (InvocationTargetException e) {
                        if (e.getCause() instanceof DecoderException ex) {
                            throw ex;
                        } else if (e.getCause() instanceof InstantiationException ex) {
                            if (ex.getCause() instanceof DecoderException ex2) {
                                throw ex2;
                            }
                        }
                        throw new RuntimeException("Couldn't decode packet " + construct.getClass().getName() + " because it threw an exception.", e);
                    }
                })
                .encoder(BasePacket::toBytes)
                .consumer((msg, ctx) -> {
                    return msg.handle(ctx);
                })
                .add();

        return id;
    }
}
