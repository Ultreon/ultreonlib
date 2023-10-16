package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.mods.lib.client.gui.v2.util.IntSize;
import com.ultreon.mods.lib.input.GameKeyboard;

public interface IMcOperatingSystem {
    void shutdown(McApplication executor) throws AccessDeniedException;

    IntSize getScreenSize();

    void raiseHardError(McApplication executor, Throwable throwable) throws AccessDeniedException;

    OsLogger getLogger();

    void addKeyboardHook(McKeyboardHook mcKeyboardHook);

    void removeKeyboardHook(McKeyboardHook mcKeyboardHook);

    GameKeyboard.Modifier getMetaKey();
}
