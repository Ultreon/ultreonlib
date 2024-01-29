package com.ultreon.mods.lib.client.tests;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.ultreon.mods.lib.client.gui.widget.PushButton.Type.DARK;
import static com.ultreon.mods.lib.client.gui.widget.PushButton.Type.LIGHT;

@TestScreenInfo("Dirt Background Hello World")
@ApiStatus.Internal
public class TestDirtBGHelloScreen extends ULibScreen implements TestScreen {
    private PushButton.Type currentType = DARK;

    public TestDirtBGHelloScreen() {
        super(TestLaunchContext.get().title);
    }

    @Override
    public void initWidgets() {
        super.initWidgets();

        add(PushButton.of("Hello World", (btn) -> {
            this.currentType = this.currentType == DARK ? LIGHT : DARK;
            btn.type(this.currentType);
        })).type(this.currentType)
                .position(vec -> vec.set(width / 2 - 50, height / 2 - 10))
                .size(vec -> vec.set(100, 20));

        add(PushButton.of("Close", (btn) -> this.close()))
                .position(vec -> vec.set(width / 2 - 50, height / 2 + 10))
                .size(vec -> vec.set(100, 20));
    }

    @Override
    public void renderBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(renderer);
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.render(renderer, mouseX, mouseY, partialTicks);
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return new Vec2(width - 10, 6);
    }
}
