package dev.ultreon.mods.lib.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class GameKeyboard {
    private static long window = -1L;

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
        return InputConstants.isKeyDown(getWindow(), keyCode);
    }

    private static long getWindow() {
        if (window == -1) window = Minecraft.getInstance().getWindow().getWindow();
        return window;
    }

    public static boolean isKeyDown(Modifier modifier) {
        return modifier.isEitherDown();
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

        public boolean isLeftDown() {
            return isKeyDown(left);
        }

        public boolean isEitherDown() {
            return isLeftDown() || isRightDown();
        }

        public boolean isBothDown() {
            return isLeftDown() && isRightDown();
        }

        public boolean isRightDown() {
            return isKeyDown(right);
        }
    }
}
