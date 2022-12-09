package com.ultreon.mods.lib.core.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BiomeUtils extends UtilityClass {
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("#", Pattern.LITERAL);

    public static boolean doesMatch(Biome biome, String input) {
        if (biome == parse(input)) {
            return true;
        }

        String category = CATEGORY_PATTERN.matcher(input).replaceFirst(Matcher.quoteReplacement(""));
        return biome.getBiomeCategory().name().equalsIgnoreCase(category);
    }

    public static boolean contains(Biome biome, Collection<? extends String> list, boolean valueIfListEmpty) {
        if (list.isEmpty()) {
            return valueIfListEmpty;
        }

        for (String str : list) {
            if (doesMatch(biome, str)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public static Biome parse(String str) {
        ResourceLocation id = ResourceLocation.tryParse(str);

        if (id != null) {
            return ForgeRegistries.BIOMES.getValue(id);
        }

        return null;
    }
}
