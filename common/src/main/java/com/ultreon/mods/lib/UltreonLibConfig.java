package com.ultreon.mods.lib;

import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.screen.TitleStyle;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraftforge.common.ForgeConfigSpec;

public class UltreonLibConfig {
    // Config specifications.
    public static final ForgeConfigSpec CLIENT;
    public static final ForgeConfigSpec COMMON;
    public static final ForgeConfigSpec SERVER;

    // Values
    public static final ForgeConfigSpec.EnumValue<Theme> THEME;
    public static final ForgeConfigSpec.EnumValue<TitleStyle> TITLE_STYLE;
    public static final ForgeConfigSpec.BooleanValue WINDOW_MANAGER;


    // Initialization
    static {
        //****************//
        //     CLIENT     //
        //****************//
        var client = new ForgeConfigSpec.Builder();

        client.push("gui");
        {
            THEME = client
                    .comment("The theme to use for the GUI")
                    .defineEnum("theme", Theme.DARK);
            TITLE_STYLE = client
                    .comment("The style of the title")
                    .defineEnum("title_style", TitleStyle.DETACHED);
        }
        client.pop();

        client.push("tests");
        {
            WINDOW_MANAGER = client
                    .comment("Test for Window Manager.\nThis makes it possible to spawn movable windows on your screen (inside Minecraft ofc)")
                    .define("window_manager", false);
        }
        client.pop();


        CLIENT = client.build();

        //****************//
        //     COMMON     //
        //****************//
        var common = new ForgeConfigSpec.Builder();

        COMMON = common.build();

        //****************//
        //     SERVER     //
        //****************//
        var server = new ForgeConfigSpec.Builder();

        SERVER = server.build();

    }

    @ExpectPlatform
    public static void register(Object context) {
        throw new AssertionError();
    }
}
