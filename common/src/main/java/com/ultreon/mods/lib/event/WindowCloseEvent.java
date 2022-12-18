package com.ultreon.mods.lib.event;

import com.mojang.blaze3d.platform.Window;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;

@FunctionalInterface
public interface WindowCloseEvent {
    /**
     * Interrupt with true to cancel the closing of the window.
     */
    Event<WindowCloseEvent> EVENT = EventFactory.createEventResult();

    EventResult onWindowClose(Window window, Source source);

    enum Source {
        GENERIC,
        QUIT_BUTTON,
        OTHER
    }
}
