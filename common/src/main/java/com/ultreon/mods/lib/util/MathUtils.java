package com.ultreon.mods.lib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;

import java.math.BigDecimal;
import java.util.Random;

@SuppressWarnings("unused")
public final class MathUtils extends UtilityClass {
    private static final double DOUBLES_EQUAL_PRECISION = 0.000000001;
    private static final Random RANDOM = new Random();

    /**
     * EnvTypeance between two {@link Vec3i} (such as {@link BlockPos}).
     * Consider using {@link #distanceSq} when possible.
     *
     * @param from one point
     * @param to   another point
     * @return the distance between {@code from} and {@code to}
     */
    public static double distance(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dz = to.getZ() - from.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * EnvTypeance between two {@link Position}. Consider using {@link #distanceSq} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to}
     */
    public static double distance(Position from, Position to) {
        double dx = to.x() - from.x();
        double dy = to.y() - from.y();
        double dz = to.z() - from.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * EnvTypeance between an entity's position and a given position. Consider using {@link
     * #distanceSq} when possible.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos}
     */
    public static double distance(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dy = pos.getY() + 0.5 - entity.getY();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * EnvTypeance between an entity's position and a given position. Consider using {@link
     * #distanceSq} when possible.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos}
     */
    public static double distance(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dy = pos.y() - entity.getY();
        double dz = pos.z() - entity.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * EnvTypeance squared between two {@link Vec3i} (such as {@link BlockPos}).
     * Use instead of {@link #distance} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared
     */
    public static double distanceSq(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dz = to.getZ() - from.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * EnvTypeance squared between two {@link Position}. Use instead of {@link #distance} when
     * possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared
     */
    public static double distanceSq(Position from, Position to) {
        double dx = to.x() - from.x();
        double dy = to.y() - from.y();
        double dz = to.z() - from.z();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * EnvTypeance squared between an entity's position and a given position.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos} squared
     */
    public static double distanceSq(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dy = pos.getY() + 0.5 - entity.getY();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * EnvTypeance squared between an entity's position and a given position.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos} squared
     */
    public static double distanceSq(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dy = pos.y() - entity.getY();
        double dz = pos.z() - entity.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * EnvTypeance between two {@link Vec3i} (such as {@link BlockPos}), but
     * ignores the Y-coordinate. Consider using {@link #distanceHorizontalSq} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared, ignoring Y-axis
     */
    public static double distanceHorizontal(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dz = to.getZ() - from.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * EnvTypeance between two {@link Position}, but ignores the Y-coordinate. Consider using {@link
     * #distanceHorizontalSq} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared
     */
    public static double distanceHorizontal(Position from, Position to) {
        double dx = to.x() - from.x();
        double dz = to.z() - from.z();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * EnvTypeance between an entity's position and a given position, but ignores the Y-coordinate.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos}, ignoring Y-axis
     */
    public static double distanceHorizontal(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * EnvTypeance between an entity's position and a given position, but ignores the Y-coordinate.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos}, ignoring Y-axis
     */
    public static double distanceHorizontal(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dz = pos.z() - entity.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * EnvTypeance squared between two {@link Vec3i} (such as {@link BlockPos}),
     * but ignores the Y-coordinate. Use instead of {@link #distanceHorizontal} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared, ignoring Y-axis
     */
    public static double distanceHorizontalSq(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dz = to.getZ() - from.getZ();
        return dx * dx + dz * dz;
    }

    /**
     * EnvTypeance squared between two {@link Position}, but ignores the Y-coordinate. Use instead of
     * {@link #distanceHorizontal} when possible.
     *
     * @param from one point
     * @param to another point
     * @return the distance between {@code from} and {@code to} squared, ignoring Y-axis
     */
    public static double distanceHorizontalSq(Position from, Position to) {
        double dx = to.x() - from.x();
        double dz = to.z() - from.z();
        return dx * dx + dz * dz;
    }

    /**
     * EnvTypeance squared between an entity's position and a given position, but ignores the
     * Y-coordinate.
     *
     * @param entity the entity
     * @param pos the other point
     * @return the distance between {code entity} and {@code pos} squared, ignoring Y-axis
     */
    public static double distanceHorizontalSq(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return dx * dx + dz * dz;
    }

    /**
     * EnvTypeance squared between an entity's position and a given position, but ignores the
     * Y-coordinate.
     *
     * @param entity the entity
     * @param pos    the other point
     * @return the distance between {code entity} and {@code pos} squared, ignoring Y-axis
     */
    public static double distanceHorizontalSq(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dz = pos.z() - entity.getZ();
        return dx * dx + dz * dz;
    }

    /**
     * Decimal places of a float value.
     *
     * @param d float value
     * @return the amount of decimal places.
     */
    public static int getDecimalPlaces(Float d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(Double d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(BigDecimal d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static double clamp(double value, double lowerBound, double upperBound) {
        return value < lowerBound ? lowerBound : Math.min(value, upperBound);
    }

    public static float clamp(float value, float lowerBound, float upperBound) {
        return value < lowerBound ? lowerBound : Math.min(value, upperBound);
    }

    public static int clamp(int value, int lowerBound, int upperBound) {
        return value < lowerBound ? lowerBound : Math.min(value, upperBound);
    }

    /**
     * Compare if two doubles are equal, using precision constant {@link #DOUBLES_EQUAL_PRECISION}.
     */
    public static boolean doublesEqual(double a, double b) {
        return doublesEqual(a, b, DOUBLES_EQUAL_PRECISION);
    }

    /**
     * Compare if two doubles are equal, within the given level of precision.
     *
     * @param precision Should be a small, positive number (like {@link #DOUBLES_EQUAL_PRECISION})
     */
    public static boolean doublesEqual(double a, double b, double precision) {
        return Math.abs(b - a) < precision;
    }

    /**
     * Compare if two floats are equal, using precision constant {@link #DOUBLES_EQUAL_PRECISION}.
     */
    public static boolean floatsEqual(float a, float b) {
        return floatsEqual(a, b, (float) DOUBLES_EQUAL_PRECISION);
    }

    /**
     * Compare if two floats are equal, within the given level of precision.
     *
     * @param precision Should be a small, positive number (like {@link #DOUBLES_EQUAL_PRECISION})
     */
    public static boolean floatsEqual(float a, float b, float precision) {
        return Math.abs(b - a) < precision;
    }

    public static boolean inRangeExclusive(double value, double min, double max) {
        return value < max && value > min;
    }

    public static boolean inRangeExclusive(int value, int min, int max) {
        return value < max && value > min;
    }

    public static boolean inRangeInclusive(double value, double min, double max) {
        return value <= max && value >= min;
    }

    public static boolean inRangeInclusive(int value, int min, int max) {
        return value <= max && value >= min;
    }

    public static int min(final int a, final int b) {
        return Math.min(a, b);
    }

    public static int min(int a, final int b, final int c) {
        if (b < a) a = b;
        if (c < a) a = c;
        return a;
    }

    public static int min(int a, final int b, final int c, final int d) {
        if (b < a) a = b;
        if (c < a) a = c;
        if (d < a) a = d;
        return a;
    }

    public static int min(int a, final int b, final int c, final int d, int... rest) {
        int min = min(a, b, c, d);
        for (int i : rest)
            if (i < min)
                min = i;
        return min;
    }

    public static int max(final int a, final int b) {
        return Math.max(a, b);
    }

    public static int max(int a, final int b, final int c) {
        if (b > a) a = b;
        if (c > a) a = c;
        return a;
    }

    public static int max(int a, final int b, final int c, final int d) {
        if (b > a) a = b;
        if (c > a) a = c;
        if (d > a) a = d;
        return a;
    }

    public static int max(int a, final int b, final int c, final int d, int... rest) {
        int max = max(a, b, c, d);
        for (int i : rest)
            if (i > max)
                max = i;
        return max;
    }

    public static double nextGaussian(double mean, double deviation) {
        return deviation * RANDOM.nextGaussian() + mean;
    }

    public static double nextGaussian(Random random, double mean, double deviation) {
        return deviation * random.nextGaussian() + mean;
    }

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static int nextIntInclusive(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static int nextIntInclusive(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean tryPercentage(double percent) {
        return RANDOM.nextDouble() < percent;
    }

    public static boolean tryPercentage(Random random, double percent) {
        return random.nextDouble() < percent;
    }
}
