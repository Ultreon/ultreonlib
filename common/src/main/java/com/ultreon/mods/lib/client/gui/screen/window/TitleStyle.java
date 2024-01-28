package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.TitleStyles;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The title style enum class.
 * See {@link com.ultreon.mods.lib.client.TitleStyles#NORMAL} for the default one.
 *
 * @author XyperCode
 */
public abstract class TitleStyle {
    private static final Map<String, TitleStyle> REGISTRY_MAP = new HashMap<>();
    private static TitleStyle[] VALUES = new TitleStyle[0];

    /**
     * The title bar height for the texture.
     */
    public final int renderHeight;

    /**
     * The title bar widget height, for boundaries of the widgets in the title bar.
     */
    public final int height;

    /**
     * The style's id name.
     */
    private final String id;
    private static int nextOrdinal = 0;
    private final int ordinal;

    public TitleStyle(int renderHeight, int height, String id) {
        this.renderHeight = renderHeight;
        this.height = height;
        this.id = id;

        VALUES = ArrayUtils.add(VALUES, this);
        REGISTRY_MAP.put(id, this);
        this.ordinal = nextOrdinal++;
    }

    public static TitleStyle fromId(String id) {
        return REGISTRY_MAP.getOrDefault(id, TitleStyles.DETACHED);
    }

    /**
     * Gets the id of the title style.
     *
     * @return the id of the title style.
     */
    public String id() {
        return id;
    }

    /**
     * Get the display name of the title style.
     *
     * @return the display name.
     */
    public Component getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * Get the translation id for the name.
     *
     * @return the translation id.
     */
    private String getDescriptionId() {
        return "ultreonlib.title_style." + id;
    }

    /**
     * Get the title style after this one.
     *
     * @return the next title style.
     */
    public TitleStyle next() {
        return TitleStyle.values()[(ordinal() + 1) % TitleStyle.values().length];
    }

    private int ordinal() {
        return ordinal;
    }

    /**
     * Get the title style before this one.
     *
     * @return the previous title style.
     */
    public TitleStyle previous() {
        return TitleStyle.values()[(ordinal() - 1 + TitleStyle.values().length) % TitleStyle.values().length];
    }

    private static TitleStyle[] values() {
        return VALUES;
    }

    public abstract void renderFrame(GuiRenderer renderer, int x, int y, int width, int height, GlobalTheme theme, Component title);
}
