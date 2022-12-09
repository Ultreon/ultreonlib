package com.ultreon.mods.lib.core.util;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.IForgeRegistryEntry;

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

    public static ResourceLocation from(IForgeRegistryEntry<?> entry) {
        return checkNotNull(entry.getRegistryName());
    }

    public static ResourceLocation fromItem(ItemLike item) {
        Preconditions.checkNotNull(item.asItem(), "asItem() is null.");
        return checkNotNull(item.asItem().getRegistryName());
    }

    public static ResourceLocation fromItem(ItemStack stack) {
        return checkNotNull(stack.getItem().getRegistryName());
    }
}
