package com.ultreon.mods.lib.advancements;

import com.google.gson.JsonObject;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UseItemTrigger implements CriterionTrigger<UseItemTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(UltreonLib.MOD_ID, "use_item");
    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    @NotNull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addPlayerListener(@NotNull PlayerAdvancements playerAdvancementsIn, @NotNull Listener<Instance> listenerIn) {
        Listeners triggerListeners = this.listeners.get(playerAdvancementsIn);
        if (triggerListeners == null) {
            triggerListeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, triggerListeners);
        }
        triggerListeners.add(listenerIn);
    }

    @Override
    public void removePlayerListener(@NotNull PlayerAdvancements playerAdvancementsIn, @NotNull Listener<Instance> listenerIn) {
        Listeners triggerListeners = this.listeners.get(playerAdvancementsIn);
        if (triggerListeners != null) {
            triggerListeners.remove(listenerIn);
            if (triggerListeners.isEmpty())
                this.listeners.remove(playerAdvancementsIn);
        }
    }

    @Override
    public void removePlayerListeners(@NotNull PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @NotNull
    @Override
    public Instance createInstance(JsonObject json, @NotNull DeserializationContext context) {
        ItemPredicate itempredicate = ItemPredicate.fromJson(json.get("item"));
        Target target = Target.fromString(GsonHelper.getAsString(json, "target", "any"));
        return new Instance(itempredicate, target);
    }

    public void trigger(ServerPlayer player, ItemStack stack, Target target) {
        Listeners triggerListeners = this.listeners.get(player.getAdvancements());
        if (triggerListeners != null)
            triggerListeners.trigger(stack, target);
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
        ItemPredicate itempredicate;
        Target target;

        Instance(ItemPredicate itempredicate, Target target) {
            super(UseItemTrigger.ID, EntityPredicate.Composite.ANY);
            this.itempredicate = itempredicate;
            this.target = target;
        }

        public static Instance instance(ItemPredicate predicate, Target target) {
            return new Instance(predicate, target);
        }

        public boolean test(ItemStack stack, Target target) {
            return itempredicate.matches(stack) && (this.target == target || this.target == Target.ANY);
        }

        @NotNull
        @Override
        public JsonObject serializeToJson(@NotNull SerializationContext p_230240_1_) {
            JsonObject json = new JsonObject();
            json.add("item", this.itempredicate.serializeToJson());
            json.addProperty("target", this.target.name());
            return json;
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = new HashSet<>();

        Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ItemStack stack, Target target) {
            List<Listener<Instance>> list = null;

            for (Listener<Instance> listener : this.listeners) {
                if (listener.getTriggerInstance().test(stack, target)) {
                    if (list == null) list = new ArrayList<>();
                    list.add(listener);
                }
            }

            if (list != null) {
                for (Listener<Instance> listener1 : list)
                    listener1.run(this.playerAdvancements);
            }
        }
    }
}
