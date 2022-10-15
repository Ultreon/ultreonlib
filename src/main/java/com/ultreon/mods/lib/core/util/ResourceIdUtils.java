package com.ultreon.mods.lib.core.util;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class ResourceIdUtils extends UtilityClass {

    private static final Pattern PATTERN = Pattern.compile("([a-z0-9._-]+:)?[a-z0-9/._-]+");

    public static boolean isValid(CharSequence name) {
        return PATTERN.matcher(name).matches();
    }

    public static ResourceLocation checkNotNull(@Nullable ResourceLocation name) {
        Preconditions.checkNotNull(name, "Name is null, make sure the object has been registered correctly");
        return name;
    }

    public static ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    public static <T> ResourceLocation from(Registry<T> registry, T entry) {
        return checkNotNull(registry.getKey(entry));
    }

    public static <T> ResourceLocation from(IForgeRegistry<T> registry, T entry) {
        return checkNotNull(registry.getKey(entry));
    }

    public static ResourceLocation fromItem(ItemLike item) {
        Preconditions.checkNotNull(item.asItem(), "asItem() is null.");
        return checkNotNull(ForgeRegistries.ITEMS.getKey(item.asItem()));
    }

    public static ResourceLocation fromItem(ItemStack stack) {
        return checkNotNull(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }
}
