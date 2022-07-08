package com.ultreon.modlib.silentlib.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public abstract class AbstractDimensionFilteredFeature extends Feature<DimensionFilterConfig> {
    public AbstractDimensionFilteredFeature(Codec<DimensionFilterConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DimensionFilterConfig> ctx) {
        BlockPos origin = ctx.origin();
        WorldGenLevel level = ctx.level();
        return ctx.config().matches(level.getLevel().dimension());
    }
}
