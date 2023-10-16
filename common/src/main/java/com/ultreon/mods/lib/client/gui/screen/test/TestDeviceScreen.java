package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.test.device.TestEmptyApplication;
import com.ultreon.mods.lib.client.gui.v2.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@TestScreenInfo("Window Manager")
@ApiStatus.Internal
public class TestDeviceScreen extends DeviceScreen implements TestScreen {
    private final McWindow mainWindow;

    public TestDeviceScreen() {
        super(new LaunchOptions().title(TestLaunchContext.get().title).back(Minecraft.getInstance().screen));

        this.registerApp(TestEmptyApplication.id(), TestEmptyApplication::new);

        McWindow mainWindow = this.createMainWindow();
        this.getKernel().createWindow(mainWindow);
        this.mainWindow = mainWindow;
    }

    private @NotNull McWindow createMainWindow() {
        @NotNull var window = new McWindow(this.getKernel(), 10, 10, 250, 50, "Hello World!");
        window.addOnClosingListener(() -> false);

        window.add(new McButton(10, 10, 50, 14, "Log Hello")).addClickHandler(this::logHello);
        window.add(new McButton(70, 10, 50, 14, "Shutdown")).addClickHandler(this::shutdownSystem);
        window.add(new McButton(130, 10, 50, 14, "Spawn")).addClickHandler(this::spawnNewWindow);
        window.add(new McLabel(10, 30, 240, 16, "Welcome to the world of Modded Minecraft!"));
        return window;
    }

    private void spawnNewWindow(McButton button) {
        if (minecraft.screen instanceof DeviceScreen deviceScreen) {
            try {
                assert this.minecraft != null;
                deviceScreen.getKernel().spawnApplication(new ApplicationId("com.ultreon.apps:mc-empty"), "Hello " + this.minecraft.getUser().getName());
            } catch (AppNotFoundException | McPermissionException e) {
                this.getSystem().getLogger().error("Failed to create empty window:", e);
                McMessageDialog error = McMessageDialog.create(this.getKernel(), McMessageDialog.Icons.ERROR, Component.literal("Error!"), Component.literal("Failed to spawn window."));
                this.mainWindow.openDialog(error);
            }
        }
    }

    private void shutdownSystem(McButton button) {
        try {
            this.getSystem().shutdown(this.getKernel());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    private void logHello(McButton button) {
        assert this.minecraft != null;
        UltreonLib.LOGGER.info("Hello, " + this.minecraft.getUser().getName());
    }
}
