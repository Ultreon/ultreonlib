package dev.ultreon.mods.lib.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

/// Math helper, for all your math needs.
public class MathHelper {
    private static final double DOUBLES_EQUAL_PRECISION = 0.000000001;
    private static final Random RANDOM = new Random();

    public static byte clamp(byte value, int min, int max) {
        if (value < min) return (byte) min;
        else return (byte) Math.min(value, max);
    }

    public static short clamp(short value, int min, int max) {
        if (value < min) return (short) min;
        else return (short) Math.min(value, max);
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        else return Math.min(value, max);
    }

    public static long clamp(long value, long min, long max) {
        if (value < min) return min;
        else return Math.min(value, max);
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        else return Math.min(value, max);
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        else return Math.min(value, max);
    }

    public static BigInteger clamp(BigInteger value, BigInteger min, BigInteger max) {
        return value.max(min).min(max);
    }

    public static BigDecimal clamp(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value.max(min).min(max);
    }

    public static double root(int value, int root) {
        return Math.pow(value, 1.0d / root);
    }

    public static double round(double value, int places) {
        if (((Double) value).isNaN() || ((Float) (float) value).isNaN()) {
            return value;
        }
        if (((Double) value).isInfinite() || ((Float) (float) value).isInfinite()) {
            return value;
        }

        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double lerp(double min, double max, double percentage) {
        return min + percentage * (max - min);
    }

    public static Color mixColors(Color color1, Color color2, double percent) {
        double inverse_percent = 1.0 - percent;
        int redPart = (int) (color1.getRed() * percent + color2.getRed() * inverse_percent);
        int greenPart = (int) (color1.getGreen() * percent + color2.getGreen() * inverse_percent);
        int bluePart = (int) (color1.getBlue() * percent + color2.getBlue() * inverse_percent);
        int alphaPart = (int) (color1.getAlpha() * percent + color2.getAlpha() * inverse_percent);
        return Color.rgba(redPart, greenPart, bluePart, alphaPart);
    }

    public static byte diff(byte from, byte to) {
        return (byte) (Math.max(from, to) - Math.min (from, to));
    }

    public static short diff(short from, short to) {
        return (short) (Math.max(from, to) - Math.min (from, to));
    }

    public static int diff(int from, int to) {
        return Math.max(from, to) - Math.min (from, to);
    }

    public static long diff(long from, long to) {
        return Math.max(from, to) - Math.min (from, to);
    }

    public static float diff(float from, float to) {
        return Math.max(from, to) - Math.min (from, to);
    }

    public static double diff(double from, double to) {
        return Math.max(from, to) - Math.min (from, to);
    }

    /// Distance between two [Vec3i] (such as [BlockPos]).
    /// Consider using [#distanceSq] when possible.
    ///
    /// @param from one point
    /// @param to   another point
    /// @return the distance between `from` and `to`
    public static double distance(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dz = to.getZ() - from.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /// Distance between two [Position]. Consider using [#distanceSq] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to`
    public static double distance(Position from, Position to) {
        double dx = to.x() - from.x();
        double dy = to.y() - from.y();
        double dz = to.z() - from.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /// Distance between an entity's position and a given position. Consider using
    /// [#distanceSq] when possible.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos`
    public static double distance(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dy = pos.getY() + 0.5 - entity.getY();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /// Distance between an entity's position and a given position. Consider using
    /// [#distanceSq] when possible.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos`
    public static double distance(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dy = pos.y() - entity.getY();
        double dz = pos.z() - entity.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /// Distance squared between two [Vec3i] (such as [BlockPos]).
    /// Use instead of [#distance] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared
    public static double distanceSq(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dz = to.getZ() - from.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /// Distance squared between two [Position]. Use instead of [#distance] when
    /// possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared
    public static double distanceSq(Position from, Position to) {
        double dx = to.x() - from.x();
        double dy = to.y() - from.y();
        double dz = to.z() - from.z();
        return dx * dx + dy * dy + dz * dz;
    }

    /// Distance squared between an entity's position and a given position.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos` squared
    public static double distanceSq(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dy = pos.getY() + 0.5 - entity.getY();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /// Distance squared between an entity's position and a given position.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos` squared
    public static double distanceSq(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dy = pos.y() - entity.getY();
        double dz = pos.z() - entity.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /// Distance between two [Vec3i] (such as [BlockPos]), but
    /// ignores the Y-coordinate. Consider using [#distanceHorizontalSq] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared, ignoring Y-axis
    public static double distanceHorizontal(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dz = to.getZ() - from.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /// Distance between two [Position], but ignores the Y-coordinate. Consider using
    /// [#distanceHorizontalSq] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared
    public static double distanceHorizontal(Position from, Position to) {
        double dx = to.x() - from.x();
        double dz = to.z() - from.z();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /// Distance between an entity's position and a given position, but ignores the Y-coordinate.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos`, ignoring Y-axis
    public static double distanceHorizontal(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /// Distance between an entity's position and a given position, but ignores the Y-coordinate.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos`, ignoring Y-axis
    public static double distanceHorizontal(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dz = pos.z() - entity.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /// Distance squared between two [Vec3i] (such as [BlockPos]),
    /// but ignores the Y-coordinate. Use instead of [#distanceHorizontal] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared, ignoring Y-axis
    public static double distanceHorizontalSq(Vec3i from, Vec3i to) {
        int dx = to.getX() - from.getX();
        int dz = to.getZ() - from.getZ();
        return dx * dx + dz * dz;
    }

    /// Distance squared between two [Position], but ignores the Y-coordinate. Use instead of
    /// [#distanceHorizontal] when possible.
    ///
    /// @param from one point
    /// @param to another point
    /// @return the distance between `from` and `to` squared, ignoring Y-axis
    public static double distanceHorizontalSq(Position from, Position to) {
        double dx = to.x() - from.x();
        double dz = to.z() - from.z();
        return dx * dx + dz * dz;
    }

    /// Distance squared between an entity's position and a given position, but ignores the
    /// Y-coordinate.
    ///
    /// @param entity the entity
    /// @param pos the other point
    /// @return the distance between {code entity} and `pos` squared, ignoring Y-axis
    public static double distanceHorizontalSq(Entity entity, Vec3i pos) {
        double dx = pos.getX() + 0.5 - entity.getX();
        double dz = pos.getZ() + 0.5 - entity.getZ();
        return dx * dx + dz * dz;
    }

    /// Distance squared between an entity's position and a given position, but ignores the
    /// Y-coordinate.
    ///
    /// @param entity the entity
    /// @param pos    the other point
    /// @return the distance between {code entity} and `pos` squared, ignoring Y-axis
    public static double distanceHorizontalSq(Entity entity, Position pos) {
        double dx = pos.x() - entity.getX();
        double dz = pos.z() - entity.getZ();
        return dx * dx + dz * dz;
    }

    /// Decimal places of a float value.
    ///
    /// @param d float value
    /// @return the amount of decimal places.
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

    /// Compare if two doubles are equal, using precision constant [#DOUBLES_EQUAL_PRECISION].
    public static boolean doublesEqual(double a, double b) {
        return doublesEqual(a, b, DOUBLES_EQUAL_PRECISION);
    }

    /// Compare if two doubles are equal, within the given level of precision.
    ///
    /// @param precision Should be a small, positive number (like [#DOUBLES_EQUAL_PRECISION])
    public static boolean doublesEqual(double a, double b, double precision) {
        return Math.abs(b - a) < precision;
    }

    /// Compare if two floats are equal, using precision constant [#DOUBLES_EQUAL_PRECISION].
    public static boolean floatsEqual(float a, float b) {
        return floatsEqual(a, b, (float) DOUBLES_EQUAL_PRECISION);
    }

    /// Compare if two floats are equal, within the given level of precision.
    ///
    /// @param precision Should be a small, positive number (like [#DOUBLES_EQUAL_PRECISION])
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
