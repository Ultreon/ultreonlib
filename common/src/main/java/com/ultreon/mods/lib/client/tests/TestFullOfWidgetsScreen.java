package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.TabPage;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.screen.window.TitleBarAccess;
import com.ultreon.mods.lib.client.gui.widget.Progressbar;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import com.ultreon.mods.lib.client.gui.widget.SimpleTabPage;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

@TestScreenInfo("Full of Widgets")
@ApiStatus.Internal
public class TestFullOfWidgetsScreen extends ULibScreen implements TestScreen {

    public TestFullOfWidgetsScreen() {
        super(TestLaunchContext.get().title);
    }

    @Override
    public void initWidgets() {
        TitleBarAccess titleBarAccess = this.titleBarAccess();
        titleBarAccess.addTab(new ButtonsTab());
        titleBarAccess.add(PushButton.of(CommonComponents.GUI_ACKNOWLEDGE));
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.render(renderer, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {

    }

    private class ButtonsTab extends SimpleTabPage {
    }
}
