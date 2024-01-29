package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.GenericMenuScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestsScreen;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.gui.widget.Button;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.input.GameKeyboard;
import dev.architectury.platform.Platform;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class InternalConfigScreen extends GenericMenuScreen {
    public InternalConfigScreen() {
        super(new Properties().titleLang("gui.ultreonlib.config.title"));

        addButtonRow(Component.translatable("gui.ultreonlib.config.theme").append(UltreonLib.getTheme().getDisplayName()), this::changeTheme);
        addButtonRow(Component.translatable("gui.ultreonlib.config.title_style").append(UltreonLib.getTitleStyle().getDisplayName()), this::changeTitleStyle);

        if (Platform.isDevelopmentEnvironment()) {
            addButtonRow(Component.literal("Tests"), PushButton.Type.PRIMARY, this::openTests, Button::getTooltip);
        }
    }

    private void openTests(PushButton button) {
        TestsScreen.open();
    }

    private void changeTheme(PushButton button) {
        GlobalTheme globalTheme = UltreonLib.getTheme();
        globalTheme = GameKeyboard.isShiftDown() ? globalTheme.previous() : globalTheme.next();
        UltreonLib.setTheme(globalTheme);
        button.setMessage(Component.translatable("gui.ultreonlib.config.theme").append(globalTheme.getDisplayName()));
    }

    private void changeTitleStyle(PushButton button) {
        TitleStyle titleStyle = UltreonLib.getTitleStyle();
        titleStyle = GameKeyboard.isShiftDown() ? titleStyle.previous() : titleStyle.next();
        UltreonLib.setTitleStyle(titleStyle);
        button.setMessage(Component.translatable("gui.ultreonlib.config.title_style").append(UltreonLib.getTitleStyle().getDisplayName()));
    }

    @Override
    public void initWidgets() {
        super.initWidgets();
    }
}
