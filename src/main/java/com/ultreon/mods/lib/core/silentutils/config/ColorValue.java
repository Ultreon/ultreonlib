package com.ultreon.mods.lib.core.silentutils.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.ultreon.mods.lib.core.silentutils.Color;

import java.util.function.Consumer;

/**
 * @deprecated Removed
 */
@Deprecated
public class ColorValue extends ConfigValue<Color> {
    private final int defaultValue;

    ColorValue(int defaultValue, ConfigSpecWrapper wrapper, String path, Consumer<ConfigSpec> handleSpec, Consumer<CommentedConfig> handleConfig) {
        super(wrapper, path, handleSpec, handleConfig);
        this.defaultValue = defaultValue;
    }

    public static Builder builder(ConfigSpecWrapper wrapper, String path) {
        return new Builder(wrapper, path);
    }

    @Override
    public Color get() {
        return Color.tryParse(config.get(path), defaultValue);
    }
}
