package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Fake use context for block-item uses class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FakeUseContext extends BlockPlaceContext {
    private final BlockHitResult rayTraceResult;

    public FakeUseContext(Player player, InteractionHand handIn, BlockHitResult hit) {
        super(new UseOnContext(player, handIn, hit));
        rayTraceResult = hit;
    }

    @Nonnull
    @Override
    public BlockPos getClickedPos() {
        return rayTraceResult.getBlockPos();
    }
}
