package com.ultreon.mods.lib.core.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Player skins.
 *
 * @author Gravestone Mod
 */
@SuppressWarnings("unused")
public class PlayerSkins {
    private static final HashMap<String, GameProfile> players = new HashMap<>();

    public static void receiveSkin(UUID uuid, String name, Consumer<ResourceLocation> consumer) {
        receiveGameProfile(uuid, name, profile -> {
            Minecraft minecraft = Minecraft.getInstance();
            Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(profile);

            if (map.containsKey(Type.SKIN)) {
                consumer.accept(minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN));
            } else {
                consumer.accept(DefaultPlayerSkin.getDefaultSkin(uuid));
            }
        });
    }

    public static void receiveGameProfile(UUID uuid, String name, Consumer<GameProfile> consumer) {
        if (players.containsKey(uuid.toString())) consumer.accept(players.get(uuid.toString()));
        else SkullBlockEntity.updateGameprofile(new GameProfile(uuid, name), gameProfile -> {
            players.put(uuid.toString(), gameProfile);
            consumer.accept(gameProfile);
        });
    }

    @Deprecated
    @Nullable
    public static ResourceLocation getSkin(UUID uuid, String name) {
        GameProfile profile = getGameProfile(uuid, name);

        if (profile == null) {
            return null;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(profile);

        if (map.containsKey(Type.SKIN)) {
            return minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
        } else {
            return DefaultPlayerSkin.getDefaultSkin(uuid);
        }
    }

    @Nullable
    @Deprecated(forRemoval = true)
    public static GameProfile getGameProfile(UUID uuid, String name) {
        return players.getOrDefault(uuid.toString(), null);
    }
}
