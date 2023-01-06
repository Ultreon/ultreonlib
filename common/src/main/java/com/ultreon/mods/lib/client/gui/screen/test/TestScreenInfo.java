package com.ultreon.mods.lib.client.gui.screen.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestScreenInfo {
    String value();
}
