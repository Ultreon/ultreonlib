package com.ultreon.mods.lib.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

@Deprecated(forRemoval = true)
public class KeyboardHelper {
    @Deprecated
    public static boolean isShiftDown() {
        return isKeyDown(InputConstants.KEY_LSHIFT) || isKeyDown(InputConstants.KEY_RSHIFT);
    }

    @Deprecated
    public static boolean isCtrlDown() {
        return isKeyDown(InputConstants.KEY_LCONTROL) || isKeyDown(InputConstants.KEY_RCONTROL);
    }

    @Deprecated
    public static boolean isAltDown() {
        return isKeyDown(InputConstants.KEY_LALT) || isKeyDown(InputConstants.KEY_RALT);
    }

    @Deprecated
    public static boolean isMetaDown() {
        return isKeyDown(InputConstants.KEY_LWIN) || isKeyDown(InputConstants.KEY_RWIN);
    }

    @Deprecated
    public static boolean isKeyDown(int keyCode) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyCode);
    }

    @Deprecated
    public static boolean isKeyDown(Modifier modifier) {
        return isKeyDown(modifier.left) || isKeyDown(modifier.right);
    }

    @Deprecated(forRemoval = true)
    public enum Modifier {
        @Deprecated
        SHIFT(InputConstants.KEY_LSHIFT, InputConstants.KEY_RSHIFT),
        @Deprecated
        CTRL(InputConstants.KEY_LCONTROL, InputConstants.KEY_RCONTROL),
        @Deprecated
        ALT(InputConstants.KEY_LALT, InputConstants.KEY_RALT),
        @Deprecated
        META(InputConstants.KEY_LWIN, InputConstants.KEY_RWIN);

        @Deprecated
        public final int left;
        @Deprecated
        public final int right;

        Modifier(int left, int right) {

            this.left = left;
            this.right = right;
        }
    }
}
