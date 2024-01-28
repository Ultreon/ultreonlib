package com.ultreon.mods.lib.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

public class UseItemTrigger extends SimpleCriterionTrigger<UseItemTrigger.Instance> {
    public void trigger(ServerPlayer player, ItemStack item) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(item));
    }

    @Override
    public @NotNull Codec<Instance> codec() {
        return RecordCodecBuilder.create((instanceCodec) -> instanceCodec
                .group(ContextAwarePredicate.CODEC.optionalFieldOf("context").forGetter((instance) -> instance.context),
                        ItemPredicate.CODEC.optionalFieldOf("item").forGetter((instance) -> instance.item),
                        Codec.STRING.fieldOf("target").forGetter((instance) -> instance.target.name().toLowerCase(Locale.ROOT)))
                .apply(instanceCodec, (context, item, target) -> new Instance(context, item, Target.fromString(target))));
    }

    public enum Target {
        BLOCK, ENTITY, ITEM, ANY;

        static Target fromString(String str) {
            for (Target t : values())
                if (t.name().equalsIgnoreCase(str))
                    return t;
            return ANY;
        }
    }

    public static class Instance implements SimpleInstance {
        Optional<ContextAwarePredicate> context;
        Optional<ItemPredicate> item;
        Target target;

        Instance(Optional<ContextAwarePredicate> context, Optional<ItemPredicate> item, Target target) {
            super();
            this.context = context;
            this.item = item;
            this.target = target;
        }

        public static Instance instance(Optional<ContextAwarePredicate> ctxPredicate, Optional<ItemPredicate> predicate, Target target) {
            return new Instance(ctxPredicate, predicate, target);
        }

        public boolean matches(ItemStack item) {
            return this.item.isEmpty() || this.item.get().matches(item);
        }

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return context;
        }
    }
}
