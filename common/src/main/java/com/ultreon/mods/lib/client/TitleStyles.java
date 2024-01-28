package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.client.gui.screen.window.DetachedTitleStyle;
import com.ultreon.mods.lib.client.gui.screen.window.HiddenTitleStyle;
import com.ultreon.mods.lib.client.gui.screen.window.NormaTitleStyle;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;

public class TitleStyles {
    /**
     * The title bar isn't shown, including the title.
     */
    public static final TitleStyle HIDDEN = new HiddenTitleStyle();
    /**
     * This is similar to the vanilla's style for container screens.
     * The title bar is merged with the frame.
     */
    public static final TitleStyle NORMAL = new NormaTitleStyle();
    /**
     * Title bar is shown, and the title is shown.
     * It's also looks detached from the frame.
     */
    public static final TitleStyle DETACHED = new DetachedTitleStyle();
}
