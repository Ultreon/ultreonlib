package com.ultreon.mods.lib.client.gui.screen.test.actionmenu;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.actionmenu.ActionMenu;
import com.ultreon.mods.lib.actionmenu.ActionMenuItem;
import com.ultreon.mods.lib.actionmenu.ActionMenuScreen;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class TestActionMenu extends ActionMenu {
    public static final ActionMenu INSTANCE = new TestActionMenu();
    private static int count = 0;

    @Override
    public void client() {
        add(new ActionMenuItem(this, UltreonLib.MOD_ID, "test_hello_world", Component.literal("Hello World Test"), () -> {
            if (Platform.isDevelopmentEnvironment()) {
                UltreonLib.LOGGER.info("Hello World!");
            }
        }));
        add(new ActionMenuItem(this, UltreonLib.MOD_ID, "test_count", Component.literal("Count"), () -> {
            if (Platform.isDevelopmentEnvironment()) {
                UltreonLib.LOGGER.info("Current count: " + ++count);
            }
        }));
        add(new ActionMenuItem(this, UltreonLib.MOD_ID, "test_go_back", Component.literal("Go Back"), () -> {
            if (Minecraft.getInstance().screen instanceof ActionMenuScreen screen) {
                screen.close();
            }
        }));
    }

    @Override
    public void server() {
        // Client-only screen.
    }
}
