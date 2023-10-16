package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import com.ultreon.mods.lib.client.gui.screen.ListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public class TestsScreen  {
    public static void open() {
        var screenTests = new ListScreen(Component.literal("Screen Tests"));
        var map = new HashMap<String, Supplier<Screen>>();

        for (var provider : UltreonLib.getScreens()) {
            try {
                var type = provider.type();
                var screenInfo = type.getAnnotation(TestScreenInfo.class);
                screenTests.addEntry(
                        screenInfo.value(),
                        type.getSimpleName(),
                        type.getName());
                map.put(type.getName(), () -> (Screen) provider.get());
            } catch (Exception e) {
                UltreonLib.LOGGER.warn("Failed to read test provider info for: " + provider.type().getName(), e);
            }
        }

        screenTests.setOnListEntryClick((list, entry) -> {
            var supplier = map.get(entry.getId());
            if (supplier != null) {
                try {
                    TestLaunchContext.withinContext(Component.nullToEmpty(entry.getTitle()), () -> {
                        var screen = supplier.get();
                        if (screen instanceof BaseScreen baseScreen) {
                            baseScreen.open();
                        } else {
                            Minecraft.getInstance().setScreen(screen);
                        }
                    });
                } catch (Exception e) {
                    UltreonLib.LOGGER.warn("Failed to open test screen: " + entry.getId(), e);
                }
            }
        });

        screenTests.setListFilter((query, id, title, description) -> {
            var content = title;

            if (query.startsWith("@")) {
                content = id;
                query = query.substring(1);
            } else if (query.startsWith("#")) {
                content = description;
                query = query.substring(1);
            }

            var found = true;
            for (var part : query.split(" ")) {
                found &= content.toLowerCase(Locale.ROOT).contains(part.toLowerCase(Locale.ROOT));
            }
            return found;
        });

        screenTests.open();
    }
}
