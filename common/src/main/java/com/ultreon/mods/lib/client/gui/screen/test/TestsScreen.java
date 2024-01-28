package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.ListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.function.Supplier;

public class TestsScreen  {
    public static void open() {
        var screenTests = new ListScreen(Component.literal("GUI Tests"));
        var map = new HashMap<String, Supplier<net.minecraft.client.gui.screens.Screen>>();

        for (var provider : UltreonLib.getScreens()) {
            try {
                var type = provider.type();
                var screenInfo = type.getAnnotation(TestScreenInfo.class);
                screenTests.addEntry(
                        screenInfo.value(),
                        type.getSimpleName(),
                        type.getName());
                map.put(type.getName(), () -> (net.minecraft.client.gui.screens.Screen) provider.get());
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
                        if (screen instanceof ULibScreen stylizedScreenUI) {
                            stylizedScreenUI.open();
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
