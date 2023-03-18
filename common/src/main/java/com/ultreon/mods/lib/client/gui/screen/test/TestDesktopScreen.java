package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.v2.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@TestScreenInfo("Custom Desktop Screen")
@ApiStatus.Internal
public class TestDesktopScreen extends DesktopScreen implements TestScreen {
    public TestDesktopScreen() {
        super(new LaunchOptions().title(TestLaunchContext.get().title).window(createMainWindow()));
        var wallpaper = new McImage(0, 0, width, height);
        wallpaper.loadFrom(new File("wallpaper.png"));
        getDesktop().setWallpaper(wallpaper);
    }

    @NotNull
    private static McWindow createMainWindow() {
        var minecraft = Minecraft.getInstance();

        @NotNull McWindow window = new McWindow(10, 10, 400, 300, Component.literal("Hello World!"));
        window.add(new McButton(10, 10, 50, 14, Component.literal("Log Hello"))).addClickHandler((button) -> UltreonLib.LOGGER.info("Hello, " + minecraft.getUser().getName()));
        window.add(new McLabel(10, 20, 240, 16, Component.literal("Welcome to the world of Modded Minecraft!")));
        return window;
    }
}
