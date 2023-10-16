package com.ultreon.mods.lib.client.gui.screen.test.device;

import com.ultreon.mods.lib.client.gui.v2.ApplicationId;
import com.ultreon.mods.lib.client.gui.v2.McApplication;
import com.ultreon.mods.lib.client.gui.v2.McWindow;
import com.ultreon.mods.lib.client.gui.v2.util.IntSize;
import net.minecraft.network.chat.Component;

import java.util.Random;

public class TestEmptyApplication extends McApplication {
    public TestEmptyApplication() {
        super(id());
    }

    public static ApplicationId id() {
        return new ApplicationId("com.ultreon.apps:mc-empty");
    }

    @Override
    public void create() {
        Random random = new Random();
        IntSize size = getSystem().getScreenSize();
        var spawn = new McWindow(this,
                random.nextInt(0, size.width - 20), random.nextInt(0, size.height - 20),
                120, 30, Component.literal("Hello!"));
        this.createWindow(spawn);
    }
}
