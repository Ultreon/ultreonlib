package com.ultreon.mods.lib.client.gui.v2;

public abstract class McKeyboardHook {
    public McKeyboardHook keyPressed(int keyCode, int scanCode, int modifiers, McKeyboardHook next) {
        return next;
    }

    public McKeyboardHook keyReleased(int keyCode, int scanCode, int modifiers, McKeyboardHook next) {
        return next;
    }

    public McKeyboardHook charTyped(char character, int modifiers, McKeyboardHook next) {
        return next;
    }
}
