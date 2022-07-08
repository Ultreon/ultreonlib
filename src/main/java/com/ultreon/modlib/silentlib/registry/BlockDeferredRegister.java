package com.ultreon.modlib.silentlib.registry;

import com.ultreon.modlib.api.holders.BlockHolder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockDeferredRegister extends DeferredRegisterWrapper<Block> {

    private final List<BlockHolder> allBlocks = new ArrayList<>();

    public BlockDeferredRegister(String modid) {
        super(modid, ForgeRegistries.BLOCKS);
    }

    public <BLOCK extends Block> BlockRegistryObject<BLOCK> register(String name, Supplier<? extends BLOCK> sup) {
        BlockRegistryObject<BLOCK> registeredBlock = register(name, sup, BlockRegistryObject::new);
        allBlocks.add(registeredBlock);
        return registeredBlock;
    }

    public List<BlockHolder> getAllBlocks() {
        return allBlocks;
    }
}