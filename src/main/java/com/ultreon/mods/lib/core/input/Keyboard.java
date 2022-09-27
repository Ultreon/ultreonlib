package com.ultreon.mods.lib.core.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

/**
 * Keyboard helper.
 * Check for holding shift, ctrl or alt.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public class Keyboard {
    private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();

    /**
     * Check if the user is holding down the Shift key.
     *
     * @return true if pressed, false otherwise.
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    /**
     * Check if the user is holding down the Ctrl (Control) key.
     *
     * @return true if pressed, false otherwise.
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    /**
     * Check if the user is holding down the Alt key.
     *
     * @return true if pressed, false otherwise.
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingAlt() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_ALT);
    }

    /**
     * Check if the user is holding down the super key (for most people known as the Windows key).<br>
     * NOTE: It's not 100% guaranteed that it works, if the operating system captures the key it might not work.
     *
     * @return true if pressed, false otherwise.
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingSuper() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SUPER) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SUPER);
    }
}
