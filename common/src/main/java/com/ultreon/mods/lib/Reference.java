package com.ultreon.mods.lib;

import de.marhali.json5.Json5;

public class Reference {
    public static final Json5 JSON5 = Json5.builder(builder -> {
        builder.quoteless();
        builder.indentFactor(4);

        return builder.build();
    });
}
