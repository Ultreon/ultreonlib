package com.ultreon.modlib.silentlib.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredRegisterWrapper<T extends IForgeRegistryEntry<T>> {

    protected final DeferredRegister<T> internal;

    protected DeferredRegisterWrapper(String modid, IForgeRegistry<T> registry) {
        internal = DeferredRegister.create(registry, modid);
    }

    /**
     * Api Note: For use with custom registries
     */
    protected DeferredRegisterWrapper(String modid, Class<T> base) {
        internal = DeferredRegister.create(base, modid);
    }

    protected <I extends T, W extends RegistryObjectWrapper<I>> W register(String name, Supplier<? extends I> sup, Function<RegistryObject<I>, W> objectWrapper) {
        return objectWrapper.apply(internal.register(name, sup));
    }

    /**
     * Only call this from mekanism and for custom registries
     */
    public void createAndRegister(IEventBus bus, String name) {
        internal.makeRegistry(name, RegistryBuilder::new);
        register(bus);
    }

    /**
     * Only call this from mekanism and for custom registries
     */
    public void createAndRegisterWithTags(IEventBus bus, String name, String tagFolder) {
        internal.makeRegistry(name, () -> new RegistryBuilder<T>().tagFolder(tagFolder));
        register(bus);
    }

    public void register(IEventBus bus) {
        internal.register(bus);
    }
}