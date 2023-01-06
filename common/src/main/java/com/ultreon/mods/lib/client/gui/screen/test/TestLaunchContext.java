package com.ultreon.mods.lib.client.gui.screen.test;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

public class TestLaunchContext {
    private static final ThreadLocal<TestLaunchContext> INSTANCE = new ThreadLocal<>();

    public final Component title;

    private TestLaunchContext(Component title) {
        this.title = title;
    }

    public static TestLaunchContext get() {
        return Objects.requireNonNull(INSTANCE.get());
    }

    @ApiStatus.Internal
    public static void withinContext(Component title, Runnable function) {
        if (TestLaunchContext.INSTANCE.get() != null) return;
        TestLaunchContext.INSTANCE.set(new TestLaunchContext(title));
        try {
            function.run();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        TestLaunchContext.INSTANCE.remove();
    }
}
