package com.ultreon.mods.lib.client.gui.screen.test;

import com.ultreon.mods.lib.client.devicetest.DeviceScreen;
import com.ultreon.mods.lib.client.gui.screen.test.device.TestEmptyApplication;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;

@TestScreenInfo("Window Manager")
@ApiStatus.Internal
public class TestDeviceScreen extends DeviceScreen implements TestScreen {
    public TestDeviceScreen() {
        super(new LaunchOptions().title(TestLaunchContext.get().title).fullscreen().back(Minecraft.getInstance().screen));

        this.registerApp(TestEmptyApplication.id(), TestEmptyApplication::new);
    }
}
