package com.ultreon.mods.lib;

import com.ultreon.mods.lib.client.gui.DevPreviewScreen;
import com.ultreon.mods.lib.util.UtilityClass;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.ArrayList;
import java.util.List;

public final class DevPreviewRegistry extends UtilityClass {
    private static final List<Mod> MODS = new ArrayList<>();

    public static void register(String modId) {
        register(Platform.getMod(modId));
    }

    public static void register(Mod mod) {
        MODS.add(mod);
    }

    static CompoundEventResult<Screen> onTitleScreen(Screen screen) {
        if (screen instanceof TitleScreen titleScreen && !DevPreviewScreen.isInitialized()) {
            if (MODS.isEmpty()) return CompoundEventResult.pass();
            return CompoundEventResult.interruptTrue(new DevPreviewScreen(MODS, titleScreen));
        }
        return CompoundEventResult.pass();
    }
}
