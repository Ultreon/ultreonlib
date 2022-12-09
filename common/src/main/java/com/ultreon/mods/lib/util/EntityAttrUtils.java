package com.ultreon.mods.lib.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class EntityAttrUtils extends UtilityClass {
    public static void apply(LivingEntity entity, Attribute attribute, AttributeModifier modifier) {
        AttributeInstance instance = entity.getAttribute(attribute);
        apply(instance, modifier);
    }

    public static void apply(AttributeInstance attributeInstance, AttributeModifier modifier) {
        if (attributeInstance == null) return;
        AttributeModifier currentMod = attributeInstance.getModifier(modifier.getId());

        if (currentMod != null && (!Meth.doublesEqual(currentMod.getAmount(), modifier.getAmount()) || currentMod.getOperation() != modifier.getOperation())) {
            // Modifier changed, so it needs to be reapplied
            attributeInstance.removeModifier(currentMod);
        } else {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void remove(LivingEntity entity, Attribute attribute, UUID uuid) {
        AttributeInstance instance = entity.getAttribute(attribute);
        remove(instance, uuid);
    }

    public static void remove(AttributeInstance attributeInstance, UUID uuid) {
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(uuid);
    }
}
