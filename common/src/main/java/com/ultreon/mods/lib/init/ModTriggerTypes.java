package com.ultreon.mods.lib.init;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.advancements.UseItemTrigger;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ModTriggerTypes {
    private static final DeferredRegister<CriterionTrigger<?>> REGISTRAR = DeferredRegister.create(UltreonLib.MOD_ID, Registries.TRIGGER_TYPE);
    
    public static final RegistrySupplier<UseItemTrigger> USE_ITEM = REGISTRAR.register(UseItemTrigger.ID, UseItemTrigger::new);
    
    public static void init() {
        REGISTRAR.register();
    }
}
