package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.screen.GenericMenuScreen;
import com.ultreon.mods.lib.client.gui.screen.TitleStyle;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import com.ultreon.mods.lib.util.KeyboardHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class InternalConfigScreen extends GenericMenuScreen {

    public InternalConfigScreen(Screen back) {
        super(new Properties().titleLang("gui.ultreonlib.config.title"));

        addButtonRow(Component.translatable("gui.ultreonlib.config.theme").append(UltreonLib.getTheme().getDisplayName()), this::changeTheme);
        addButtonRow(Component.translatable("gui.ultreonlib.config.title_style").append(UltreonLib.getTitleStyle().getDisplayName()), this::changeTitleStyle);
    }

    private void changeTheme(BaseButton button) {
        new RuntimeException().printStackTrace(System.out);
        Theme theme = UltreonLib.getTheme();
        theme = KeyboardHelper.isShiftDown() ? theme.previous() : theme.next();
        UltreonLib.setTheme(theme);
        button.setMessage(Component.translatable("gui.ultreonlib.config.theme").append(theme.getDisplayName()));
    }

    private void changeTitleStyle(BaseButton button) {
        TitleStyle titleStyle = UltreonLib.getTitleStyle();
        titleStyle = KeyboardHelper.isShiftDown() ? titleStyle.previous() : titleStyle.next();
        UltreonLib.setTitleStyle(titleStyle);
        button.setMessage(Component.translatable("gui.ultreonlib.config.title_style").append(UltreonLib.getTitleStyle().getDisplayName()));
    }

    @Override
    protected void init() {
        super.init();
    }
}
