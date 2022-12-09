package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.client.gui.screen.window.ScreenHooks;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;

public class UltreonLibClient {
    private static UltreonLibClient instance;

    @ApiStatus.Internal
    public static UltreonLibClient create() {
        if (instance != null) {
            throw new IllegalStateException("The mod is already instantiated.");
        }
        instance = new UltreonLibClient();
        return instance;
    }

    public UltreonLibClient() {
        ClientLifecycleEvent.CLIENT_SETUP.register(this::clientSetup);
        ClientScreenInputEvent.MOUSE_DRAGGED_PRE.register(ScreenHooks::onMouseDrag);
        ClientScreenInputEvent.MOUSE_CLICKED_PRE.register(ScreenHooks::onMouseClick);
        ClientScreenInputEvent.MOUSE_RELEASED_PRE.register(ScreenHooks::onMouseRelease);
        ClientScreenInputEvent.MOUSE_SCROLLED_PRE.register(ScreenHooks::onMouseScroll);
        ClientScreenInputEvent.KEY_PRESSED_PRE.register(ScreenHooks::onKeyPress);
        ClientScreenInputEvent.KEY_RELEASED_PRE.register(ScreenHooks::onKeyRelease);
        ClientScreenInputEvent.CHAR_TYPED_PRE.register(ScreenHooks::onCharTyped);
        ClientGuiEvent.RENDER_PRE.register((screen, matrices, mouseX, mouseY, delta) -> ScreenHooks.onDrawScreen(screen, matrices, mouseX, mouseY, delta));
    }

    private void clientSetup(Minecraft minecraft) {

    }
}
