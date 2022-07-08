package com.ultreon.modlib.silentlib.network.internal;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * More or less a straight copy from {@link net.minecraftforge.network.PlayMessages.SpawnEntity}.
 */
public class SpawnEntityPacket {
    private final Entity entity;
    private final ResourceLocation typeId;
    private final int entityId;
    private final UUID uuid;
    private final double posX, posY, posZ;
    private final byte pitch, yaw, headYaw;
    private final int velX, velY, velZ;
    private final FriendlyByteBuf buf;

    public SpawnEntityPacket(Entity e) {
        this.entity = e;
        this.typeId = e.getType().getRegistryName();
        this.entityId = e.getId();
        this.uuid = e.getUUID();
        this.posX = e.getX();
        this.posY = e.getY();
        this.posZ = e.getZ();
        this.pitch = (byte) Mth.floor(e.getXRot() * 256.0F / 360.0F);
        this.yaw = (byte) Mth.floor(e.getYRot() * 256.0F / 360.0F);
        this.headYaw = (byte) (e.getYHeadRot() * 256.0F / 360.0F);
        Vec3 vec3d = e.getDeltaMovement();
        double d1 = Mth.clamp(vec3d.x, -3.9D, 3.9D);
        double d2 = Mth.clamp(vec3d.y, -3.9D, 3.9D);
        double d3 = Mth.clamp(vec3d.z, -3.9D, 3.9D);
        this.velX = (int) (d1 * 8000.0D);
        this.velY = (int) (d2 * 8000.0D);
        this.velZ = (int) (d3 * 8000.0D);
        this.buf = null;
    }

    private SpawnEntityPacket(ResourceLocation typeId, int entityId, UUID uuid, double posX, double posY, double posZ, byte pitch, byte yaw, byte headYaw, int velX, int velY, int velZ, FriendlyByteBuf buf) {
        this.entity = null;
        this.typeId = typeId;
        this.entityId = entityId;
        this.uuid = uuid;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.pitch = pitch;
        this.yaw = yaw;
        this.headYaw = headYaw;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.buf = buf;
    }

    public static void encode(SpawnEntityPacket msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.typeId);
        buf.writeInt(msg.entityId);
        buf.writeLong(msg.uuid.getMostSignificantBits());
        buf.writeLong(msg.uuid.getLeastSignificantBits());
        buf.writeDouble(msg.posX);
        buf.writeDouble(msg.posY);
        buf.writeDouble(msg.posZ);
        buf.writeByte(msg.pitch);
        buf.writeByte(msg.yaw);
        buf.writeByte(msg.headYaw);
        buf.writeShort(msg.velX);
        buf.writeShort(msg.velY);
        buf.writeShort(msg.velZ);
        if (msg.entity instanceof IEntityAdditionalSpawnData) {
            ((IEntityAdditionalSpawnData) msg.entity).writeSpawnData(buf);
        }
    }

    public static SpawnEntityPacket decode(FriendlyByteBuf buf) {
        return new SpawnEntityPacket(
                buf.readResourceLocation(),
                buf.readInt(),
                new UUID(buf.readLong(), buf.readLong()),
                buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readByte(), buf.readByte(), buf.readByte(),
                buf.readShort(), buf.readShort(), buf.readShort(),
                buf
        );
    }

    public static void handle(SpawnEntityPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(msg.typeId);
            if (type == null) {
                throw new RuntimeException(String.format("Could not spawn entity (key %d) with unknown type at (%f, %f, %f)", msg.entityId, msg.posX, msg.posY, msg.posZ));
            }

            Optional<Level> world = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());
            Entity e = world.map(type::create).orElse(null);
            if (e == null) {
                return;
            }

            e.setPacketCoordinates(msg.posX, msg.posY, msg.posZ);
            e.absMoveTo(msg.posX, msg.posY, msg.posZ, (msg.yaw * 360) / 256.0F, (msg.pitch * 360) / 256.0F);
            e.setYHeadRot((msg.headYaw * 360) / 256.0F);
            e.setYBodyRot((msg.headYaw * 360) / 256.0F);

            e.setId(msg.entityId);
            e.setUUID(msg.uuid);
            world.filter(ClientLevel.class::isInstance).ifPresent(w -> ((ClientLevel) w).putNonPlayerEntity(msg.entityId, e));
            e.lerpMotion(msg.velX / 8000.0, msg.velY / 8000.0, msg.velZ / 8000.0);
            if (e instanceof IEntityAdditionalSpawnData) {
                ((IEntityAdditionalSpawnData) e).readSpawnData(msg.buf);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

