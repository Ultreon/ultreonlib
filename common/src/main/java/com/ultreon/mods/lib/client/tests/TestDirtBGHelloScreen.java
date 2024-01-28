package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.ultreon.mods.lib.client.gui.widget.PushButton.Type.DARK;
import static com.ultreon.mods.lib.client.gui.widget.PushButton.Type.LIGHT;

@TestScreenInfo("Fullscreen Render Screen")
@ApiStatus.Internal
public class TestDirtBGHelloScreen extends ULibScreen implements TestScreen {
    public static final ResourceLocation BACKGROUND_LOCATION = UltreonLib.res("tests/fullscreen_render/background");
    private PushButton.Type currentType = DARK;

    public TestDirtBGHelloScreen() {
        super(TestLaunchContext.get().title);
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        clearWidgets();

        add(PushButton.of("Hello World", (btn) -> {
            this.currentType = this.currentType == DARK ? LIGHT : DARK;
            btn.type(this.currentType);
        })).type(this.currentType);

        add(PushButton.of("Close", (btn) -> this.back()));
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
