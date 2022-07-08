package com.ultreon.modlib.utils;

import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class ParticleUtils {
    private ParticleUtils() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static <T extends ParticleOptions> void spawn(Level world, T type, Vec3i pos, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        spawn(world, type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, particleCount, xOffset, yOffset, zOffset, speed);
    }

    public static <T extends ParticleOptions> void spawn(Level world, T type, double x, double y, double z, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        if (world instanceof ServerLevel) {
            ((ServerLevel) world).sendParticles(type, x, y, z, particleCount, xOffset, yOffset, zOffset, speed);
        }
    }
}
