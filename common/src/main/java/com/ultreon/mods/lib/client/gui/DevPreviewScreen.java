package com.ultreon.mods.lib.client.gui;

import com.google.errorprone.annotations.concurrent.LazyInit;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import com.ultreon.mods.lib.event.WindowCloseEvent;
import dev.architectury.platform.Mod;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Iterator;
import java.util.List;

@ApiStatus.Internal
public class DevPreviewScreen extends ULibScreen {
    private static final Component DESCRIPTION = Component.translatable("screen.ultreonlib.dev_preview.description");
    private final List<String> modNames;
    private final TitleScreen titleScreen;
    private final String modNamesStr;
    private @LazyInit PushButton continueBtn;
    private @LazyInit PushButton cancelBtn;
    private static boolean initialized;

    public DevPreviewScreen(List<Mod> mods, TitleScreen titleScreen) {
        super(Component.translatable("screen.ultreonlib.dev_preview.title"));

        this.modNames = mods.stream().map(Mod::getName).toList();
        this.titleScreen = titleScreen;
        this.modNamesStr = String.join(", ", modNames);
    }

    @Override
    public void initWidgets() {
        this.continueBtn = add(PushButton.of(CommonComponents.GUI_PROCEED, button -> {
            assert minecraft != null;
            minecraft.setScreen(titleScreen);
        }).position(() -> new Vector2i(width / 2 - 105, 0)).size(() -> new Vector2i(100, 20)));
        this.cancelBtn = add(PushButton.of(CommonComponents.GUI_CANCEL, button -> {
            assert minecraft != null;
            if (WindowCloseEvent.EVENT.invoker().onWindowClose(minecraft.getWindow(), WindowCloseEvent.Source.OTHER).isTrue()) {
                return;
            }
            minecraft.destroy();
        }).position(() -> new Vector2i(width / 2 + 5, 0)).size(() -> new Vector2i(100)));

        initialized = true;
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return null;
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(renderer, mouseX, mouseY, partialTicks);

        int maxWidth = width - 100;
        int modLinesHeight = font.wordWrapHeight(modNamesStr, maxWidth);
        int lineHeight = 20 + 16 + modLinesHeight + 16 + font.lineHeight + 24 + font.lineHeight * 2;

        renderer.pushPose();
        renderer.scale(2, 2, 2);
        renderer.textCenter(title, (width / 2) / 2, (height / 2 - lineHeight / 2 + font.lineHeight) / 2, 0xffaa00);
        renderer.popPose();

        renderer.textCenter(DESCRIPTION, width / 2, height / 2 - lineHeight / 2 + font.lineHeight / 2 + 24 + font.lineHeight * 2, 0xffffff);

        int y = height / 2 - lineHeight / 2 + 16 + font.lineHeight + 24 + font.lineHeight * 2 + font.lineHeight / 2;

        for(Iterator<FormattedCharSequence> var7 = font.split(Component.literal(modNamesStr), maxWidth).iterator(); var7.hasNext(); y += 9) {
            FormattedCharSequence formattedCharSequence = var7.next();
            renderer.textCenter(formattedCharSequence, width / 2, y, 0xaaaaaa);
        }
        
        continueBtn.setY(y + 16);
        cancelBtn.setY(y + 16);

        super.render(renderer, mouseX, mouseY, partialTicks);
    }

    public List<String> getModNames() {
        return modNames;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
