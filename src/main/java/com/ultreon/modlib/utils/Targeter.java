package com.ultreon.modlib.utils;


import com.google.common.annotations.Beta;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Targeter for finding the target entity from an entity.
 * Ported from spigot plugin QServerCore.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@Beta
public final class Targeter {
    private Targeter() {
        throw ExceptionUtil.utilityConstructor();
    }

    @Nullable
    public static <T extends Entity> Entity getTarget(Player player) {
        float rotX = player.getXRot();
        float rotY = player.getYRot();

        Vec3 eye = player.getEyePosition(1.0F);

        float f2 = Mth.cos(-rotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-rotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-rotX * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-rotX * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 6;

        Vec3 hitLocation = eye.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        if (Minecraft.getInstance().level != null) {
            HitResult clipHit = Minecraft.getInstance().level.clip(new ClipContext(eye, hitLocation, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (clipHit.getType() != HitResult.Type.MISS) {
                hitLocation = clipHit.getLocation();
            }

            HitResult entityHit = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, eye, hitLocation, player.getBoundingBox().inflate(5.0D), entity -> !entity.equals(player));
            if (entityHit != null) {
                clipHit = entityHit;
            }
            if (clipHit.getType() == HitResult.Type.ENTITY) {
                @SuppressWarnings("ConstantConditions") EntityHitResult entityHitResult = (EntityHitResult) clipHit;
                return entityHitResult.getEntity();
            } else {
                return null;
            }
        }
        return null;
    }

}