package com.ultreon.mods.lib.dev;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.ultreon.mods.lib.dev.network.DevNetwork;
import com.ultreon.mods.lib.util.ModMessages;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class UltreonLibDev {
    private static volatile @Nullable UltreonLibDev instance;
    private DevNetwork network;

    private UltreonLibDev() {
        instance = this;

        CommandRegistrationEvent.EVENT.register(DevCommands::register);

        LifecycleEvent.SETUP.register(this::setup);
    }

    @ApiStatus.Internal
    @CanIgnoreReturnValue
    public static UltreonLibDev init() {
        if (instance != null) {
            throw new IllegalStateException("The mod is already instantiated.");
        }

        return new UltreonLibDev();
    }

    public static @Nullable UltreonLibDev get() {
        return instance;
    }

    private void setup() {
        this.network = new DevNetwork();
        network.init();

        ModMessages.addMessage("UltreonLib", Component.literal("Dev mode enabled."));
    }

    public DevNetwork getNetwork() {
        return network;
    }
}
