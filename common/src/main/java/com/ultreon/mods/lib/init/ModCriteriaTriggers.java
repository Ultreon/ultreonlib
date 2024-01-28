package com.ultreon.mods.lib.init;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.advancements.UseItemTrigger;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ModCriteriaTriggers {
    private static final DeferredRegister<CriterionTrigger<?>> REGISTER = DeferredRegister.create(UltreonLib.MOD_ID, Registries.TRIGGER_TYPE);

    public static final RegistrySupplier<UseItemTrigger> USE_ITEM = REGISTER.register("use_item", UseItemTrigger::new);

    public static void register() {
        REGISTER.register();
    }
}
