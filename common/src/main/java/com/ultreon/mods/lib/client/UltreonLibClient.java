package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.client.gui.screen.window.ScreenHooks;
import com.ultreon.mods.lib.event.WindowCloseEvent;
import com.ultreon.mods.lib.mixin.common.ButtonAccessor;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import java.util.List;
import java.util.Optional;

public class UltreonLibClient {
    private static UltreonLibClient instance;
    private boolean callbackSetUp;

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
        ClientGuiEvent.RENDER_PRE.register(ScreenHooks::onDrawScreen);

        ClientGuiEvent.SET_SCREEN.register(this::onTitleScreenInit);
    }

    /**
     * Sets everything up when the title screen is shown.
     *
     * @param screen the initialized screen. (Only used if it's the title screen)
     */
    private CompoundEventResult<Screen> onTitleScreenInit(Screen screen) {
        Minecraft client = Minecraft.getInstance();

        // Only if it's the title screen.
        if (screen instanceof TitleScreen titleScreen) {
            // Set everything up.
            setupGLFWCallback(client);
            overrideQuitButton(client, titleScreen);
        }

        return CompoundEventResult.pass();
    }

    /**
     * Overrides the quit button action.
     *
     * @param client the minecraft client.
     * @param titleScreen the title screen.
     */
    private void overrideQuitButton(Minecraft client, TitleScreen titleScreen) {
        // Get all gui objects from the title screen.
        List<? extends GuiEventListener> buttons = titleScreen.children();

        // Intercepting close button.
        Optional<? extends Button> quitButton = buttons.stream().filter(listener -> listener instanceof Button button && button.getMessage().equals(Component.translatable("menu.quit"))).map(listener -> (Button) listener).findFirst();

        // Only override if the quit button is found.
        quitButton.ifPresent(widget -> {
            // Override on press field. (Requires access widener)
            ((ButtonAccessor)widget).setOnPress((button) -> {
                EventResult result = WindowCloseEvent.EVENT.invoker().onWindowClose(client.getWindow(), WindowCloseEvent.Source.GENERIC);
                if (result.isFalse()) {
                    client.stop();
                }
            });
        });
    }

    /**
     * Sets up the {@link GLFW#glfwSetWindowCloseCallback(long, GLFWWindowCloseCallbackI)}  window close callback using GLFW}.
     * @param client the minecraft client.
     * @see GLFW#glfwSetWindowCloseCallback(long, GLFWWindowCloseCallbackI)
     */
    @SuppressWarnings("resource")
    private void setupGLFWCallback(Minecraft client) {
        if (!callbackSetUp) {
            // Intercepting close button / ALT+F4 (on Windows and Ubuntu)
            long handle = client.getWindow().getWindow();

            // Set the callback.
            GLFW.glfwSetWindowCloseCallback(handle, window -> {
                EventResult result = WindowCloseEvent.EVENT.invoker().onWindowClose(client.getWindow(), WindowCloseEvent.Source.GENERIC);
                if (result.isFalse()) {
                    GLFW.glfwSetWindowShouldClose(window, false);
                }
            });
            callbackSetUp = true;
        }
    }
    private void clientSetup(Minecraft minecraft) {

    }
}
