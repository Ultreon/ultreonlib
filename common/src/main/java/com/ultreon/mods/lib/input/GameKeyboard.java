package com.ultreon.mods.lib.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class GameKeyboard {
    public static boolean isShiftDown() {
        return isKeyDown(InputConstants.KEY_LSHIFT) || isKeyDown(InputConstants.KEY_RSHIFT);
    }

    public static boolean isCtrlDown() {
        return isKeyDown(InputConstants.KEY_LCONTROL) || isKeyDown(InputConstants.KEY_RCONTROL);
    }

    public static boolean isAltDown() {
        return isKeyDown(InputConstants.KEY_LALT) || isKeyDown(InputConstants.KEY_RALT);
    }

    public static boolean isMetaDown() {
        return isKeyDown(InputConstants.KEY_LWIN) || isKeyDown(InputConstants.KEY_RWIN);
    }

    public static boolean isKeyDown(int keyCode) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyCode);
    }

    public static boolean isKeyDown(Modifier modifier) {
        return isKeyDown(modifier.left) || isKeyDown(modifier.right);
    }

    public enum Modifier {
        SHIFT(InputConstants.KEY_LSHIFT, InputConstants.KEY_RSHIFT),
        CTRL(InputConstants.KEY_LCONTROL, InputConstants.KEY_RCONTROL),
        ALT(InputConstants.KEY_LALT, InputConstants.KEY_RALT),
        META(InputConstants.KEY_LWIN, InputConstants.KEY_RWIN);

        public final int left;
        public final int right;

        Modifier(int left, int right) {

            this.left = left;
            this.right = right;
        }
    }
}
