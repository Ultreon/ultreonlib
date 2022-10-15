package com.ultreon.mods.lib.core.player;

import com.ultreon.mods.lib.core.ModdingLibrary;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModdingLibrary.MOD_ID)
public class ModMessages {
    private static final List<Component> MESSAGES = new ArrayList<>();

    public static void addMessage(Component component) {
        MESSAGES.add(component);
    }

    @SubscribeEvent
    public static void sendOnLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player == null) return;

        for (Component message : MESSAGES) {
            player.sendSystemMessage(message);
        }
    }
}
