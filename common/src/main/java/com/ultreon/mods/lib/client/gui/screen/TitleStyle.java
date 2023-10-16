package com.ultreon.mods.lib.client.gui.screen;

import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.network.chat.Component;

/**
 * The title style enum class.
 * See {@link #NORMAL} for the default one.
 *
 * @author XyperCode
 */
public enum TitleStyle {
    /**
     * The title bar isn't shown, including the title.
     */
    HIDDEN(4, 0, "hidden"),

    /**
     * This is similar to the vanilla's style for container screens.
     * The title bar is merged with the frame.
     */
    NORMAL(16, 16, "normal"),

    /**
     * Title bar is shown, and the title is shown.
     * It's also looks detached from the frame.
     */
    DETACHED(25, 20, "detached");

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

    TitleStyle(int renderHeight, int height, String id) {

        this.renderHeight = renderHeight;
        this.height = height;
        this.id = id;
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

    /**
     * Get the title style before this one.
     *
     * @return the previous title style.
     */
    public TitleStyle previous() {
        return TitleStyle.values()[(ordinal() - 1 + TitleStyle.values().length) % TitleStyle.values().length];
    }
}
