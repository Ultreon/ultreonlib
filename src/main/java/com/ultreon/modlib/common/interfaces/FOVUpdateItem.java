package com.ultreon.modlib.common.interfaces;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Update item for FOV (Field of View).
 *
 * @author Qboi123
 */

/**
 * Implemented on Items which update/alter FOV under certain conditions.
 */
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface FOVUpdateItem {
    float getFOVMod(ItemStack stack, Player player);
}