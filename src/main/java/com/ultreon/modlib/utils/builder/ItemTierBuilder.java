package com.ultreon.modlib.utils.builder;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Item tier builder.
 *
 * @author Qboi123
 */
public class ItemTierBuilder {
    private int harvestLevel = 0;
    private int maxUses = -1;
    private float efficiency;
    private float attackDamage;
    private int enchantability;
    private Ingredient repairMaterial;

    public ItemTierBuilder() {

    }

    public ItemTierBuilder harvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    public ItemTierBuilder maxUses(int maxUses) {
        this.maxUses = maxUses;
        return this;
    }

    public ItemTierBuilder efficiency(float efficiency) {
        this.efficiency = efficiency;
        return this;
    }

    public ItemTierBuilder attackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public ItemTierBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public ItemTierBuilder repairMaterial(Tag<Item> tag) {
        this.repairMaterial = Ingredient.of(tag);
        return this;
    }

    public ItemTierBuilder repairMaterial(ItemStack... stacks) {
        this.repairMaterial = Ingredient.of(stacks);
        return this;
    }

    public ItemTierBuilder repairMaterial(Stream<ItemStack> stacks) {
        this.repairMaterial = Ingredient.of(stacks);
        return this;
    }

    public ItemTierBuilder repairMaterial(Collection<ItemStack> stacks) {
        this.repairMaterial = Ingredient.of(stacks.stream());
        return this;
    }

    public ItemTierBuilder repairMaterial(Iterable<ItemStack> stacks) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (ItemStack stack : stacks) {
            itemStackList.add(stack);
        }
        return repairMaterial(itemStackList);
    }

    public ItemTierBuilder repairMaterial(ItemLike... items) {
        this.repairMaterial = Ingredient.of(items);
        return this;
    }

    public ItemTierBuilder repairMaterial(Ingredient ingredient) {
        this.repairMaterial = ingredient;
        return this;
    }

    public Tier build() {
        return new Tier() {
            @Override
            public int getUses() {
                return maxUses;
            }

            @Override
            public float getSpeed() {
                return efficiency;
            }

            @Override
            public float getAttackDamageBonus() {
                return attackDamage;
            }

            @Override
            public int getLevel() {
                return harvestLevel;
            }

            @Override
            public int getEnchantmentValue() {
                return enchantability;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return repairMaterial;
            }
        };
    }
}
