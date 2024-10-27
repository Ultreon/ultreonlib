package dev.ultreon.mods.lib.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class EntityAttrUtils {
    public static void apply(LivingEntity entity, Holder<Attribute> attribute, AttributeModifier modifier) {
        AttributeInstance instance = entity.getAttribute(attribute);
        apply(instance, modifier);
    }

    public static void apply(AttributeInstance attributeInstance, AttributeModifier modifier) {
        if (attributeInstance == null) return;
        AttributeModifier currentMod = attributeInstance.getModifier(modifier.id());

        if (currentMod != null && (!MathUtils.doublesEqual(currentMod.amount(), modifier.amount()) || currentMod.operation() != modifier.operation())) {
            // Modifier changed, so it needs to be reapplied
            attributeInstance.removeModifier(currentMod.id());
        } else {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void remove(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation location) {
        AttributeInstance instance = entity.getAttribute(attribute);
        remove(instance, location);
    }

    public static void remove(AttributeInstance attributeInstance, ResourceLocation location) {
        if (attributeInstance == null) return;
        attributeInstance.removeModifier(location);
    }
}
