package com.ultreon.mods.lib.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.event.WindowCloseEvent;
import dev.architectury.platform.Mod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.ApiStatus;

import java.util.Iterator;
import java.util.List;

@ApiStatus.Internal
public class DevPreviewScreen extends Screen {
    private static final Component DESCRIPTION = Component.translatable("screen.ultreonlib.dev_preview.description");
    private final List<String> modNames;
    private final TitleScreen titleScreen;
    private final String modNamesStr;
    private Button continueBtn;
    private Button cancelBtn;
    private static boolean initialized;

    public DevPreviewScreen(List<Mod> mods, TitleScreen titleScreen) {
        super(Component.translatable("screen.ultreonlib.dev_preview.title"));

        this.modNames = mods.stream().map(Mod::getName).toList();
        this.titleScreen = titleScreen;
        this.modNamesStr = String.join(", ", modNames);
    }

    @Override
    protected void init() {
        this.continueBtn = addRenderableWidget(Button.builder(CommonComponents.GUI_PROCEED, button -> {
            assert minecraft != null;
            minecraft.setScreen(titleScreen);
        }).pos(width / 2 - 105, 0).width(100).build());
        this.cancelBtn = addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> {
            assert minecraft != null;
            if (WindowCloseEvent.EVENT.invoker().onWindowClose(minecraft.getWindow(), WindowCloseEvent.Source.OTHER).isTrue()) {
                return;
            }
            minecraft.destroy();
        }).pos(width / 2 + 5, 0).width(100).build());

        initialized = true;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);

        int maxWidth = width - 100;
        int modLinesHeight = font.wordWrapHeight(modNamesStr, maxWidth);
        int lineHeight = 20 + 16 + modLinesHeight + 16 + font.lineHeight + 24 + font.lineHeight * 2;

        poseStack.pushPose();
        poseStack.scale(2, 2, 2);
        drawCenteredString(poseStack, font, title, (width / 2) / 2, (height / 2 - lineHeight / 2 + font.lineHeight) / 2, 0xffaa00);
        poseStack.popPose();

        drawCenteredString(poseStack, font, DESCRIPTION, width / 2, height / 2 - lineHeight / 2 + font.lineHeight / 2 + 24 + font.lineHeight * 2, 0xffffff);

        int y = height / 2 - lineHeight / 2 + 16 + font.lineHeight + 24 + font.lineHeight * 2 + font.lineHeight / 2;

        for(Iterator<FormattedCharSequence> var7 = font.split(Component.literal(modNamesStr), maxWidth).iterator(); var7.hasNext(); y += 9) {
            FormattedCharSequence formattedCharSequence = var7.next();
            drawCenteredString(poseStack, font, formattedCharSequence, width / 2, y, 0xaaaaaa);
        }
        
        continueBtn.setY(y + 16);
        cancelBtn.setY(y + 16);

        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    public List<String> getModNames() {
        return modNames;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
