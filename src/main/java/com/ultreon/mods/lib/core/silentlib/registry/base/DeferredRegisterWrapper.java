package com.ultreon.mods.lib.core.silentlib.registry.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @deprecated Removed
 */
@Deprecated
public class DeferredRegisterWrapper<T extends IForgeRegistryEntry<T>> {

    protected final DeferredRegister<T> internal;

    protected DeferredRegisterWrapper(String modId, IForgeRegistry<T> registry) {
        internal = DeferredRegister.create(registry, modId);
    }

    /**
     * Api Note: For use with custom registries
     */
    protected DeferredRegisterWrapper(String modId, ResourceLocation registryId) {
        internal = DeferredRegister.create(registryId, modId);
    }

    protected <I extends T, W extends RegistryObjectWrapper<I>> W register(String name, Supplier<? extends I> sup, Function<RegistryObject<I>, W> objectWrapper) {
        return objectWrapper.apply(internal.register(name, sup));
    }

    /**
     * Only call this from mekanism and for custom registries
     */
    @Deprecated
    public void createAndRegister(IEventBus bus, String name) {
        // no-op
    }

    /**
     * Only call this from mekanism and for custom registries
     */
    @Deprecated
    public void createAndRegisterWithTags(IEventBus bus, Class<T> type, @Deprecated String tagFolder) {
        createAndRegisterWithTags(bus, type);
    }

    /**
     * Only call this from mekanism and for custom registries
     */
    public void createAndRegisterWithTags(IEventBus bus, Class<T> type) {
        internal.makeRegistry(type, RegistryBuilder::new);
        register(bus);
    }

    public void register(IEventBus bus) {
        internal.register(bus);
    }
}