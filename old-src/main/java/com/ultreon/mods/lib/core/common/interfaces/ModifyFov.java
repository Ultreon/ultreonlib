package com.ultreon.mods.lib.core.common.interfaces;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Update item for FOV (Field of View).
 *
 * @author Qboi123
 */
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ModifyFov {
    float getFovMod(ItemStack stack, Player player);
}