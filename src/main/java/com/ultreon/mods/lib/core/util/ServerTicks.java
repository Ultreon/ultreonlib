/*
 * forge-1.12.2-QModLib_main
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ultreon.mods.lib.core.util;

import com.ultreon.mods.lib.core.ModdingLibrary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class ServerTicks {
    private static final ServerTicks INSTANCE = new ServerTicks();
    private static final int QUEUE_OVERFLOW_LIMIT = 200;

    @SuppressWarnings("FieldMayBeFinal")
    private volatile Queue<Runnable> scheduledActions = new ConcurrentLinkedDeque<>();

    private ServerTicks() {
        MinecraftForge.EVENT_BUS.addListener(this::serverTicks);
    }

    public static void scheduleAction(Runnable action) {
        INSTANCE.scheduledActions.add(action);

        if (INSTANCE.scheduledActions.size() > QUEUE_OVERFLOW_LIMIT) {
            ModdingLibrary.LOGGER.warn("Too many server tick actions queued! Currently at " + INSTANCE.scheduledActions.size() + " items. Would have added '" + action + "'.",
                    new IllegalStateException("ServerTicks queue overflow"));
            INSTANCE.scheduledActions.clear();
        }
    }

    private void serverTicks(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            runScheduledActions();
        }
    }

    private void runScheduledActions() {
        Runnable action = scheduledActions.poll();
        while (action != null) {
            action.run();
            action = scheduledActions.poll();
        }
    }
}
