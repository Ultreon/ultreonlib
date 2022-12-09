/*
 * GameUtil
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ultreon.mods.lib.core.util;

import net.minecraft.SharedConstants;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class GameUtil extends UtilityClass {
    /**
     * Set to true to override the development environment flag.
     * Then set {@link #devEnv} to the desired overridden value.
     *
     * @see #isDeveloperEnv()
     * @see #devEnv
     */
    public static boolean overrideDevEnv = false;

    /**
     * The overridden value for {@link #isDeveloperEnv()}.
     * Note that {@link #overrideDevEnv} needs to be set to {@code true} to make it work.
     *
     * @see #isDeveloperEnv()
     * @see #overrideDevEnv
     */
    public static boolean devEnv = false;

    /**
     * Check if the game is run as client side.
     *
     * @return true if we are on the client side.
     */
    public static boolean isClient() {
        return FMLEnvironment.dist.isClient();
    }

    /**
     * Check if the game is run as server side.
     *
     * @return true if we are on the server side.
     */
    public static boolean isServer() {
        return FMLEnvironment.dist.isDedicatedServer();
    }

    /**
     * Check if this is a development (non-{@linkplain #isProductionEnv() production} environment.
     * Use {@link #devEnv} and {@link #overrideDevEnv} to override this value.
     *
     * @return True if and only if we are running in the development environment
     * @see #isProductionEnv()
     */
    public static boolean isDeveloperEnv() {
        if (overrideDevEnv) {
            return devEnv;
        }
        return !FMLEnvironment.production;
    }

    /**
     * Check if this is a production (non-{@linkplain #isDeveloperEnv() development}) environment.
     *
     * @return True if and only if we are running in the development environment
     * @see #isDeveloperEnv()
     */
    public static boolean isProductionEnv() {
        if (overrideDevEnv) {
            return !devEnv;
        }
        return FMLEnvironment.production;
    }

    /**
     * Make Minecraft think you're running in an IDE.
     *
     * @param yesOrNo true (yes) to enable.
     * @see SharedConstants#IS_RUNNING_IN_IDE
     */
    public static void setRunningInIDE(boolean yesOrNo) {
        SharedConstants.IS_RUNNING_IN_IDE = yesOrNo;
    }
}
