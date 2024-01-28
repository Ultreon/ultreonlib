package com.ultreon.mods.lib.commons.util;

import com.ultreon.mods.lib.commons.UtilityClass;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TimeUtils extends UtilityClass {
    /**
     * @param duration the desired duration to format.
     * @return the formatted duration.
     * @deprecated use date time library of CoreLibs instead.
     */
    @NotNull
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static String formatDuration(long duration) {
        LocalDateTime g = LocalDateTime.ofEpochSecond(duration, 0, ZoneOffset.ofTotalSeconds(0));
        int minute = g.getMinute();
        int second = g.getSecond();

        double hourDouble = (double) duration / 60 / 60;
        return formatDuration(minute, second, hourDouble);
    }

    /**
     * @deprecated use date time library of CoreLibs instead.
     */
    @NotNull
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static String formatDuration(int minute, int second, double hourDouble) {
        hourDouble -= (double) minute / 60;
        hourDouble -= (double) second / 60 / 60;

        int hour = (int) hourDouble;

        String minuteString = Integer.toString(minute);
        String secondString = Integer.toString(second);

        if (minuteString.length() == 1) minuteString = "0" + minuteString;
        if (secondString.length() == 1) secondString = "0" + secondString;

        return hour + ":" + minuteString + ":" + secondString;
    }
}
