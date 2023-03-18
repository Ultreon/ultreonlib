package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import com.ultreon.mods.lib.client.gui.screen.ListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.*;
import java.util.function.Supplier;

public class TestsScreen  {
    private static final List<ServiceLoader.Provider<TestScreen>> SCREENS;

    static {
        var load = ServiceLoader.load(TestScreen.class);
        List<ServiceLoader.Provider<TestScreen>> list = new ArrayList<>();
        try {
            list = load.stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SCREENS = list;
    }

    public static void open(final Screen back) {
        var screenTests = new ListScreen(Component.literal("Screen Tests"));
        var map = new HashMap<String, Supplier<Screen>>();

        for (var screen : SCREENS) {
            try {
                var type = screen.type();
                var screenInfo = type.getAnnotation(TestScreenInfo.class);
                screenTests.addEntry(
                        screenInfo.value(),
                        type.getSimpleName(),
                        type.getName());
                map.put(type.getName(), () -> (Screen) screen.get());
            } catch (Exception e) {
                e.printStackTrace();
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
                    e.printStackTrace();
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
