package com.ultreon.mods.lib;

import com.ultreon.mods.lib.client.gui.screen.TitleStyle;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import dev.architectury.platform.Platform;
import io.github.xypercode.craftyconfig.ConfigEntry;
import io.github.xypercode.craftyconfig.ConfigInfo;
import io.github.xypercode.craftyconfig.CraftyConfig;
import net.minecraft.resources.ResourceLocation;

@ConfigInfo(fileName = "ultreonlib")
public class UltreonLibConfig extends CraftyConfig {
    @ConfigEntry(path = "gui.theme", comment = "The theme to use for the GUI")
    public static ResourceLocation theme = GlobalTheme.VANILLA.getId();

    @ConfigEntry(path = "gui.titleStyle", comment = "The style of the title")
    public static TitleStyle titleStyle = TitleStyle.DETACHED;

    @ConfigEntry(path = "tests.windowManager", comment = "If the Window Manager test should be enabled.")
    public static boolean windowManager = UltreonLib.isDevEnv();

    @ConfigEntry(path = "advanced.enforceDevMode", comment = "Whether or not to enforce development mode.")
    public static boolean enforceDevMode = false;
}
