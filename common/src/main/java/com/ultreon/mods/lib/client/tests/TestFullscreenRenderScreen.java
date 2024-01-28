package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.FullscreenRenderScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.widget.toolbar.ToolbarButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@TestScreenInfo("Fullscreen Render Screen")
@ApiStatus.Internal
public class TestFullscreenRenderScreen extends FullscreenRenderScreen implements TestScreen {
    public static final ResourceLocation BACKGROUND_LOCATION = UltreonLib.res("tests/fullscreen_render/background");

    public TestFullscreenRenderScreen() {
        super(TestLaunchContext.get().title);

        addToolbarItem(new ToolbarButton(60, Component.literal("Hello!"), button -> UltreonLib.LOGGER.info("Hello World! (From Fullscreen Render Screen)")));
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        gfx.blitSprite(BACKGROUND_LOCATION, 0, 0, width, height);

        gfx.drawString(this.font, "Hello World", 13, 13, 0xdddddd, true);
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return new Vec2(width - 10, 6);
    }
}
