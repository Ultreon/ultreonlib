package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.widget.Progressbar;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

@TestScreenInfo("Progress Screen")
@ApiStatus.Internal
public class TestProgressScreen extends ULibScreen implements TestScreen {
    private PushButton proceedBtn;
    private Progressbar progressbar;
    private int progress = 0;

    public TestProgressScreen() {
        super(TestLaunchContext.get().title, null);
    }

    @Override
    protected void initWidgets() {
        progressbar = this.add(new Progressbar(500)
                .position(() -> new Vector2i(width / 2, height / 2)));
        proceedBtn = this.add(PushButton.of(CommonComponents.GUI_PROCEED, (btn) -> back())
                .position(() -> new Vector2i(width / 2 + 91 - 50, height / 2 + 3 + 5))
                .size(() -> new Vector2i(50, 20)));
        proceedBtn.active = false;
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx, mouseX, mouseY, partialTicks);

        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        progress++;
        if (progress >= progressbar.getMaximum()) {
            progress = progressbar.getMaximum();
            proceedBtn.active = true;
        }
        progressbar.setValue(progress);
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        if (!proceedBtn.active) return null;
        return new Vec2(width - 10, 6);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
