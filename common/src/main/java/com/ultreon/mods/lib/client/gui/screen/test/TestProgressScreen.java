package com.ultreon.mods.lib.client.gui.screen.test;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import com.ultreon.mods.lib.client.gui.widget.Button;
import com.ultreon.mods.lib.client.gui.widget.Progressbar;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@TestScreenInfo("Progress Screen")
@ApiStatus.Internal
public class TestProgressScreen extends BaseScreen implements TestScreen {
    private BaseButton proceedBtn;
    private Progressbar progressbar;
    private int progress = 0;

    public TestProgressScreen() {
        super(TestLaunchContext.get().title, null);
    }

    @Override
    protected void init() {
        progressbar = this.addRenderableWidget(new Progressbar(width / 2, height / 2, 500));
        proceedBtn = this.addRenderableWidget(new Button(width / 2 + 91 - 50, height / 2 + 3 + 5, 50, 20, CommonComponents.GUI_PROCEED, (btn) -> back()));
        proceedBtn.active = false;
    }

    @Override
    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        renderBackground(pose);

        super.render(pose, mouseX, mouseY, partialTicks);
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
