package com.ultreon.mods.lib.client.tests;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestLaunchContext;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreenInfo;
import com.ultreon.mods.lib.client.gui.widget.PushButton;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TestScreenInfo("Utilities")
@ApiStatus.Internal
public class UtilitiesScreen extends ULibScreen implements TestScreen {

    public UtilitiesScreen() {
        super(TestLaunchContext.get().title, null);
    }

    private static void dumpGlobalThemes(PushButton caller) {
        for (GlobalTheme globalTheme : GlobalTheme.getThemes()) {
            if (globalTheme == null) throw new NullPointerException("globalTheme == null");
            GlobalTheme.CODEC.encodeStart(JsonOps.INSTANCE, globalTheme)
                    .resultOrPartial(UltreonLib.LOGGER::warn)
                    .ifPresent(element -> writeTheme(globalTheme, element));
        }
    }

    private static void writeTheme(GlobalTheme globalTheme, JsonElement element) {
        String fileName = "global_themes/" + globalTheme.getId().toString().replace(":", "/") + ".json";
        File file = new File(fileName);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            UltreonLib.LOGGER.warn("Failed to create themes directory: " + file.getParentFile().getAbsolutePath());
            return;
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            UltreonLib.GSON.toJson(element, writer);
        } catch (IOException e) {
            UltreonLib.LOGGER.warn("Failed to write theme file: " + fileName, e);
        }
    }

    @Override
    public void initWidgets() {
        this.add(PushButton.of("Dump Global Themes", UtilitiesScreen::dumpGlobalThemes)
                .position(() -> new Vector2i(width / 2 - 100, height / 2 + 3))
                .size(() -> new Vector2i(200, 20)));

        this.add(PushButton.of("Back", btn -> close())
                .position(() -> new Vector2i(width / 2, height / 2 + 3 + 20))
                .size(() -> new Vector2i(100, 20)));
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.render(renderer, mouseX, mouseY, partialTicks);
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
        return true;
    }
}
