package com.ultreon.mods.lib.client.gui.screen.window;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.util.KeyboardHelper;
import dev.architectury.event.EventResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ScreenHooks {
    public static EventResult onMouseDrag(Minecraft client, Screen screen, double mouseX1, double mouseY1, int button, double mouseX2, double mouseY2) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());
        if (button == 0) {
            Window win = WindowManager.INSTANCE.getDraggingWindow();
            if (win != null) {
                eventResult.set(EventResult.interruptFalse());
                win.mouseDragged(mouseX1, mouseY1, button, mouseX2, mouseY2);
            } else {
                WindowManager.INSTANCE.getWindowAt(mouseX1, mouseY1).ifPresent(w -> {
                    eventResult.set(EventResult.interruptFalse());
                    w.mouseDragged(mouseX1, mouseY1, button, mouseX2, mouseY2);
                });
            }
        }
        return eventResult.get();
    }

    public static EventResult onMouseClick(Minecraft client, Screen screen, double mouseX, double mouseY, int button) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        if (button == 0) {
            WindowManager.INSTANCE.getWindowAt(mouseX, mouseY).ifPresent(w -> {
                eventResult.set(EventResult.interruptFalse());
                w.mouseClicked(mouseX, mouseY, button);
                WindowManager.INSTANCE.moveToFront(w);
            });
        }

        return eventResult.get();
    }


    public static EventResult onMouseRelease(Minecraft client, Screen screen, double mouseX, double mouseY, int button) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        if (button == 0) {
            WindowManager.INSTANCE.getWindowAt(mouseX, mouseY).ifPresent(w -> {
                eventResult.set(EventResult.interruptFalse());
                w.mouseReleased(mouseX, mouseY, button);
            });
        }

        return eventResult.get();
    }

    public static EventResult onMouseScroll(Minecraft client, Screen screen, double mouseX, double mouseY, double amount) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        WindowManager.INSTANCE.getWindowAt(mouseX, mouseY).ifPresent(w -> {
            eventResult.set(EventResult.interruptFalse());
            w.mouseScrolled(mouseX, mouseY, amount);
        });

        return eventResult.get();
    }

    public static boolean onMouseMoved(long windowPtr, double mouseX, double mouseY) {
        Optional<Window> windowAt = WindowManager.INSTANCE.getWindowAt(mouseX, mouseY);
        if (windowAt.isPresent()) {
            Window w = windowAt.get();
            w.mouseMoved(mouseX, mouseY);
            return true;
        }
        return false;
    }

    public static EventResult onKeyPress(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        WindowManager.INSTANCE.getForegroundWindow().ifPresent(w -> {
            eventResult.set(EventResult.interruptFalse());
            w.keyPressed(keyCode, scanCode, modifiers);
        });

        if (KeyboardHelper.isCtrlDown() && KeyboardHelper.isAltDown() && KeyboardHelper.isShiftDown() && keyCode == GLFW.GLFW_KEY_ESCAPE) {
            WindowManager.INSTANCE.closeAllWindows();
        }

        if (KeyboardHelper.isCtrlDown() && keyCode == GLFW.GLFW_KEY_DOWN) {
            WindowManager.INSTANCE.getForegroundWindow().ifPresent(Window::hide);
        }

        if (KeyboardHelper.isCtrlDown() && keyCode == GLFW.GLFW_KEY_Q) {
            WindowManager.INSTANCE.getForegroundWindow().ifPresent(Window::close);
        }

        if (UltreonLibConfig.WINDOW_MANAGER.get() && keyCode == GLFW.GLFW_KEY_RIGHT_CONTROL) {
            Random random = new Random();
            new Window(random.nextInt(5, screen.width - 105), random.nextInt(5, screen.height - 105), 100, 100).show();
        }

        return eventResult.get();
    }

    public static EventResult onKeyRelease(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        WindowManager.INSTANCE.getForegroundWindow().ifPresent(w -> {
            eventResult.set(EventResult.interruptFalse());
            w.keyReleased(keyCode, scanCode, modifiers);
        });

        return eventResult.get();
    }

    public static EventResult onCharTyped(Minecraft client, Screen screen, char character, int keyCode) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        WindowManager.INSTANCE.getForegroundWindow().ifPresent(w -> {
            eventResult.set(EventResult.interruptFalse());
            w.charTyped(character, 0);
        });

        return eventResult.get();
    }

    public static EventResult onDrawScreen(Screen screen, PoseStack matrices, int mouseX, int mouseY, float delta) {
        AtomicReference<EventResult> eventResult = new AtomicReference<>(EventResult.pass());

        WindowManager.INSTANCE.renderAllWindows(matrices, mouseX, mouseY, delta);

        return eventResult.get();
    }
}
