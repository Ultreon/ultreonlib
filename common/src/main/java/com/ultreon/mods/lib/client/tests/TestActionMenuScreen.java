package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.actionmenu.ActionMenu;
import com.ultreon.mods.lib.actionmenu.ActionMenuScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.screen.test.actionmenu.TestActionMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

@TestScreenInfo("Action Menu Test")
public class TestActionMenuScreen extends ActionMenuScreen implements TestScreen {
    public TestActionMenuScreen() {
        this(Minecraft.getInstance().screen, TestActionMenu.INSTANCE, 0, Component.literal("Test Menu"));
    }

    protected TestActionMenuScreen(@Nullable Screen parent, ActionMenu menu, int menuIndex) {
        super(parent, menu, menuIndex);
    }

    protected TestActionMenuScreen(@Nullable Screen parent, ActionMenu menu, int menuIndex, Component title) {
        super(parent, menu, menuIndex, title);
    }
}
