/*
 * QModLib - LogUtils
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

package com.ultreon.modlib.silentlib.util;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogUtils {
    public void noticeableWarning(Logger logger, boolean trace, List<String> lines) {
        logger.error("********************************************************************************");

        for (final String line : lines) {
            for (final String subLine : wrapString(line, 78, false, new ArrayList<>())) {
                logger.error("* " + subLine);
            }
        }

        if (trace) {
            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (int i = 2; i < 8 && i < stackTrace.length; i++) {
                logger.warn("*  at {}{}", stackTrace[i].toString(), i == 7 ? "..." : "");
            }
        }

        logger.error("********************************************************************************");
    }

    @SuppressWarnings("SameParameterValue")
    private static List<String> wrapString(String string, int lnLength, boolean wrapLongWords, List<String> list) {
        final String[] lines = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(System.lineSeparator());
        Collections.addAll(list, lines);
        return list;
    }
}
