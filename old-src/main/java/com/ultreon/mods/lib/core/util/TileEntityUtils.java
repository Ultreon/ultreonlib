package com.ultreon.mods.lib.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

/**
 * Tile entity utilities.
 *
 * @author MrCrayfish
 */
public final class TileEntityUtils {
    private TileEntityUtils() {
        throw Exceptions.utilityConstructor();
    }

    /**
     * Sends an update packet to clients tracking a tile entity.
     *
     * @param tileEntity the tile entity to update
     */
    @Deprecated(forRemoval = true)
    public static void sendUpdatePacket(BlockEntity tileEntity) {
//        Packet<ClientGamePacketListener> packet = tileEntity.getUpdatePacket();
//        if (packet != null) {
//            sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
//        }
    }

    /**
     * Sends an update packet to clients tracking a tile entity with a specific CompoundNBT
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(BlockEntity tileEntity, CompoundTag compound) {
        ClientboundBlockEntityDataPacket packet = ClientboundBlockEntityDataPacket.create(tileEntity, blockEntity -> compound);
        sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
    }

    private static void sendUpdatePacket(Level world, BlockPos pos, ClientboundBlockEntityDataPacket packet) {
        if (world instanceof ServerLevel server) {
            List<ServerPlayer> players = server.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }
}
