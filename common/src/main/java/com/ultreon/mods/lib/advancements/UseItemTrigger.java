package com.ultreon.mods.lib.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UseItemTrigger extends SimpleCriterionTrigger<UseItemTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(UltreonLib.MOD_ID, "use_item");

    @NotNull
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public @NotNull Criterion<Instance> createCriterion(Instance instance) {
        return new Criterion<>(this, instance);
    }

    public void trigger(ServerPlayer player, ItemStack item) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(item));
    }

    @Override
    public @NotNull Codec<Instance> codec() {
        return RecordCodecBuilder.create((instanceCodec) -> instanceCodec.group(
                ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Instance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::getItem),
                Codec.STRING.xmap(Target::fromString, Target::name).fieldOf("target").forGetter(Instance::getTarget)
        ).apply(instanceCodec, Instance::new));
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

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class Instance implements SimpleInstance {
        private final Optional<ItemPredicate> item;
        private final Target target;
        private final Optional<ContextAwarePredicate> player;

        Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item, Target target) {
            super();
            this.item = item;
            this.target = target;
            this.player = player;
        }

        public static Instance instance(Optional<ContextAwarePredicate> ctxPredicate, Optional<ItemPredicate> predicate, Target target) {
            return new Instance(ctxPredicate, predicate, target);
        }

        public boolean matches(ItemStack item) {
            return this.item.isEmpty() || this.item.get().matches(item);
        }

        public Optional<ItemPredicate> getItem() {
            return item;
        }

        public Target getTarget() {
            return target;
        }

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
}
