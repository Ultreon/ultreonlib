package dev.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Panorama screen, for rendering a panorama in the background of your screen / menu.
 *
 * @author XyperCode
 */
@Deprecated(forRemoval = true)
public abstract class PanoramaScreen extends BaseScreen {
    public static final PanoramaRenderer PANORAMA = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    public static final ResourceLocation PANORAMA_OVERLAY = ResourceLocation.tryParse("textures/gui/title/background/panorama_overlay.png");

    /**
     * Panorama screen constructor.
     *
     * @param title screen title.
     */
    protected PanoramaScreen(Component title) {
        super(title);
    }
}
