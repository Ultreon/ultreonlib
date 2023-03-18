package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.v2.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;

@TestScreenInfo("Custom Desktop Screen")
@ApiStatus.Internal
public class TestDesktopScreen extends DesktopScreen implements TestScreen {
    public TestDesktopScreen() {
        super(new LaunchOptions().title(TestLaunchContext.get().title).window(createMainWindow()).back(Minecraft.getInstance().screen));
        var wallpaper = new McImage(0, 0, width, height);
        wallpaper.loadFrom(new File("wallpaper.png"));
        getDesktop().setWallpaper(wallpaper);
    }

    @NotNull
    private static McWindow createMainWindow() {
        var minecraft = Minecraft.getInstance();

        @NotNull
        var window = new McWindow(10, 10, 400, 300, Component.literal("Hello World!"));

        window.add(new McButton(10, 10, 50, 14, Component.literal("Log Hello"))).addClickHandler((button) -> UltreonLib.LOGGER.info("Hello, " + minecraft.getUser().getName()));
        window.add(new McButton(70, 10, 50, 14, Component.literal("Shutdown"))).addClickHandler((button) -> {
            if (minecraft.screen instanceof DesktopScreen desktopScreen) {
                desktopScreen.shutdown();
            } else {
                minecraft.setScreen(null);
            }
        });
        window.add(new McButton(130, 10, 50, 14, Component.literal("Spawn"))).addClickHandler((button) -> {
            if (minecraft.screen instanceof DesktopScreen desktopScreen) {
                Random random = new Random();
                var spawn = new McWindow(random.nextInt(0, desktopScreen.width - 20), random.nextInt(0, desktopScreen.height - 20), 50, 16, Component.literal("Hello!"));
                desktopScreen.getDesktop().createWindow(spawn);
            }
        });
        window.add(new McLabel(10, 20, 240, 16, Component.literal("Welcome to the world of Modded Minecraft!")));
        return window;
    }
}
