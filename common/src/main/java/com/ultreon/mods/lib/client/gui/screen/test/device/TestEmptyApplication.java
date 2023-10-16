package com.ultreon.mods.lib.client.gui.screen.test.device;

import com.ultreon.mods.lib.client.devicetest.ApplicationId;
import com.ultreon.mods.lib.client.devicetest.Application;
import com.ultreon.mods.lib.client.devicetest.Window;
import com.ultreon.mods.lib.client.devicetest.sizing.IntSize;
import net.minecraft.network.chat.Component;

import java.util.Random;

public class TestEmptyApplication extends Application {
    public TestEmptyApplication() {
        super(id());
    }

    public static ApplicationId id() {
        return new ApplicationId("com.ultreon.apps:empty");
    }

    @Override
    public void create() {
        Random random = new Random();
        IntSize size = getSystem().getScreenSize();
        var spawn = new Window(this,
                random.nextInt(0, size.width - 20), random.nextInt(0, size.height - 20),
                120, 30, Component.literal("Hello!"));
        this.createWindow(spawn);
    }
}
