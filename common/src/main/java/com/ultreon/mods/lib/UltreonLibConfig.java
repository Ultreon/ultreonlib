package com.ultreon.mods.lib;

import com.ultreon.mods.lib.client.TitleStyles;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.neoforged.neoforge.common.ModConfigSpec;

public class UltreonLibConfig {
    // Config specifications.
    public static final ModConfigSpec CLIENT;
    public static final ModConfigSpec COMMON;
    public static final ModConfigSpec SERVER;

    // Values
    public static final ModConfigSpec.ConfigValue<String> THEME;
    public static final ModConfigSpec.ConfigValue<String> TITLE_STYLE;
    public static final ModConfigSpec.BooleanValue WINDOW_MANAGER;


    // Initialization
    static {
        //****************//
        //     CLIENT     //
        //****************//
        var client = new ModConfigSpec.Builder();

        client.push("gui");
        {
            THEME = client
                    .comment("The theme to use for the GUI")
                    .define("theme", GlobalTheme.VANILLA.getId().toString());
            TITLE_STYLE = client
                    .comment("The style of the title")
                    .define("title_style", TitleStyles.DETACHED.id());
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
        var common = new ModConfigSpec.Builder();

        COMMON = common.build();

        //****************//
        //     SERVER     //
        //****************//
        var server = new ModConfigSpec.Builder();

        SERVER = server.build();

    }

    @ExpectPlatform
    public static void register(Object context) {
        throw new AssertionError();
    }
}
