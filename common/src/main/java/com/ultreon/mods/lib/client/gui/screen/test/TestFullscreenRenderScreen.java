package com.ultreon.mods.lib.client.gui.screen.test;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.FullscreenRenderScreen;
import com.ultreon.mods.lib.client.gui.widget.toolbar.ToolbarButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@TestScreenInfo("Fullscreen Render Screen")
@ApiStatus.Internal
public class TestFullscreenRenderScreen extends FullscreenRenderScreen implements TestScreen {
    public TestFullscreenRenderScreen() {
        super(TestLaunchContext.get().title);

        addToolbarItem(new ToolbarButton(0, 0, 60, Component.literal("Hello!"), button -> UltreonLib.LOGGER.info("Hello World! (From Fullscreen Render Screen)")));
    }

    @Override
    protected void init() {

    }

    @Override
    public void renderBackground(@NotNull PoseStack poseStack) {
        fill(poseStack, 0, 0, width, height, 0xff333333);
        fill(poseStack, 10, 10, width - 10, height - 40, 0xff555555);
        fill(poseStack, 11, 23, width - 11, height - 41, 0xff333333);

        font.draw(poseStack, "Hello World", 13, 13, 0xdddddd);
    }

    @Override
    public void tick() {

    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return new Vec2(width - 10, 6);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
