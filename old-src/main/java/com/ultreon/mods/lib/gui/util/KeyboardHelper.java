package com.ultreon.mods.lib.gui.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class KeyboardHelper {
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
}
