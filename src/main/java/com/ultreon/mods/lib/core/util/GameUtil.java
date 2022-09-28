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

import net.minecraftforge.fml.loading.FMLEnvironment;

public final class GameUtil extends UtilityClass {
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
     * Check if this is a deobfuscated (development) environment.
     * @return True if and only if we are running in a deobfuscated environment
     */
    public static boolean isDeobfuscated() {
        return !FMLEnvironment.production;
    }
}
