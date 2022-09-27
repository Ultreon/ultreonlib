package com.ultreon.mods.lib.core.util.builder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Armor material builder.
 *
 * @author Qboi123
 */
public class ArmorMaterialBuilder {
    public static class Builder implements ArmorMaterial {

        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final Supplier<Ingredient> repairMaterial;
        private final float knockbackResistance;

        public Builder(String name, int maxDamageFactor, int[] damageReductionAmounts, int enchantability, float knockbackResistance, SoundEvent equipSound, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
            this.name = name;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmounts;
            this.enchantability = enchantability;
            this.knockbackResistance = knockbackResistance;
            this.soundEvent = equipSound;
            this.toughness = toughness;
            this.repairMaterial = repairMaterialSupplier;
        }

        public int getDurabilityForSlot(EquipmentSlot slotIn) {
            return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
        }

        public int getDefenseForSlot(EquipmentSlot slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }

        public int getEnchantmentValue() {
            return this.enchantability;
        }

        public @NotNull SoundEvent getEquipSound() {
            return this.soundEvent;
        }

        public @NotNull Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
        }

        @OnlyIn(Dist.CLIENT)
        public @NotNull String getName() {
            return this.name;
        }

        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    }
}
