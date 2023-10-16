package com.ultreon.mods.lib.advancements;

import com.google.gson.JsonObject;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UseItemTrigger extends SimpleCriterionTrigger<UseItemTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(UltreonLib.MOD_ID, "use_item");

    @NotNull
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull Instance createInstance(JsonObject json, @NotNull Optional<ContextAwarePredicate> optional, @NotNull DeserializationContext deserializationContext) {
        Optional<ItemPredicate> predicate = ItemPredicate.fromJson(json.get("item"));
        Target target = Target.fromString(GsonHelper.getAsString(json, "target", "any"));
        return new Instance(optional, predicate, target);
    }

    public void trigger(ServerPlayer player, ItemStack item) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(item));
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

    public static class Instance extends AbstractCriterionTriggerInstance {
        Optional<ItemPredicate> item;
        Target target;

        Instance(Optional<ContextAwarePredicate> optional, Optional<ItemPredicate> item, Target target) {
            super(optional);
            this.item = item;
            this.target = target;
        }

        public static Instance instance(Optional<ContextAwarePredicate> ctxPredicate, Optional<ItemPredicate> predicate, Target target) {
            return new Instance(ctxPredicate, predicate, target);
        }

        public boolean matches(ItemStack item) {
            return this.item.isEmpty() || this.item.get().matches(item);
        }

        @NotNull
        @Override
        public JsonObject serializeToJson() {
            JsonObject json = new JsonObject();
            if (item.isPresent()) {
                json.add("item", this.item.get().serializeToJson());
            }
            json.addProperty("target", this.target.name());
            return json;
        }
    }
}
