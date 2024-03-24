package com.ultreon.mods.lib.registries;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;

public class ClientRegistry<T> {
    private final ResourceLocation id;
    private final Class<T> type;
    private final BiMap<ResourceLocation, T> registry = HashBiMap.create();

    public ClientRegistry(ResourceLocation id, Class<T> type) {
        this.id = id;

        this.type = type;
    }

    public static <T> ClientRegistry<T> create(ResourceLocation id, Class<T> type) {
        return new ClientRegistry<>(id, type);
    }

    public ResourceLocation getId() {
        return id;
    }

    public T get(ResourceLocation id) {
        return registry.get(id);
    }

    public ResourceLocation getId(T component) {
        return registry.inverse().get(component);
    }

    public T register(ResourceLocation id, T component) {
        registry.put(id, component);
        return component;
    }

    public Class<T> getType() {
        return type;
    }
}
