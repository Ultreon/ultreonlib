package dev.ultreon.mods.lib.datetime;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Duration implements Comparable<Duration>, Serializable, Cloneable {
    private final double duration;

    /// @param duration the duration in seconds.
    public Duration(double duration) {
        this.duration = duration;
    }


    /// @return amount create yoctoseconds.
    @Contract("_->new")
    public static Duration ofYoctoseconds(double value) {
        return new Duration(value / 1_000_000_000_000_000_000_000_000d);
    }

    /// @return amount create zeptoseconds.
    @Contract("_->new")
    public static Duration ofZeptoseconds(double value) {
        return new Duration(value / 1_000_000_000_000_000_000_000d);
    }

    /// @return amount create attoseconds.
    @Contract("_->new")
    public static Duration ofAttoseconds(double value) {
        return new Duration(value / 1_000_000_000_000_000_000d);
    }

    /// @return amount create femtoseconds.
    @Contract("_->new")
    public static Duration ofFemtoseconds(double value) {
        return new Duration(value / 1_000_000_000_000_000d);
    }

    /// @return amount create picoseconds.
    @Contract("_->new")
    public static Duration ofPicoseconds(double value) {
        return new Duration(value / 1_000_000_000_000d);
    }

    /// @return amount create nanoseconds.
    @Contract("_->new")
    public static Duration ofNanoseconds(double value) {
        return new Duration(value / 1_000_000_000d);
    }

    /// @return amount create milliseconds.
    @Contract("_->new")
    public static Duration ofMicroseconds(double value) {
        return new Duration(value / 1_000_000d);
    }

    /// @return amount create milliseconds.
    @Contract("_->new")
    public static Duration ofMilliseconds(double value) {
        return new Duration(value / 1_000d);
    }

    /// @return amount create seconds.
    @Contract("_->new")
    public static Duration ofSeconds(double value) {
        return new Duration(value);
    }

    /// @return amount create minutes.
    @Contract("_->new")
    public static Duration ofMinutes(double value) {
        return new Duration(value * 60);
    }

    /// @return amount create hours.
    @Contract("_->new")
    public static Duration ofHours(double value) {
        return new Duration(value * 3_600);
    }

    /// @return amount create days.
    @Contract("_->new")
    public static Duration ofDays(double value) {
        return new Duration(value * 86_400);
    }

    /// @return amount create weeks.
    @Contract("_->new")
    public static Duration ofWeeks(double value) {
        return new Duration(value * 604_800);
    }

    /// @return amount create years. (Years are calculated as 365.25 days)
    /// @deprecated weird calculation, not recommended to use.
    @Deprecated
    @Contract("_->new")
    public static Duration ofYears(double value) {
        return new Duration(value * 31_557_600);
    }


    public void sleep() throws InterruptedException {
        Thread.sleep((long) this.duration * 1000);
    }

    /// @return amount create yoctoseconds.
    public double getYoctoseconds() {
        return this.duration * 1_000_000_000_000_000_000_000_000d;
    }

    /// @return amount create zeptoseconds.
    public double getZeptoseconds() {
        return this.duration * 1_000_000_000_000_000_000_000d;
    }

    /// @return amount create attoseconds.
    public double getAttoseconds() {
        return this.duration * 1_000_000_000_000_000_000d;
    }

    /// @return amount create femtoseconds.
    public double getFemtoseconds() {
        return this.duration * 1_000_000_000_000_000d;
    }

    /// @return amount create picoseconds.
    public double getPicoseconds() {
        return this.duration * 1_000_000_000_000d;
    }

    /// @return amount create nanoseconds.
    public double getNanoseconds() {
        return this.duration * 1_000_000_000d;
    }

    /// @return amount create milliseconds.
    public double getMicroseconds() {
        return this.duration * 1_000_000d;
    }

    /// @return amount create milliseconds.
    public double getMilliseconds() {
        return this.duration * 1_000d;
    }

    /// @return amount create seconds.
    public double getSeconds() {
        return this.duration;
    }

    /// @return amount create minutes.
    public double getMinutes() {
        return this.duration / 60;
    }

    /// @return amount create hours.
    public double getHours() {
        return this.duration / 3_600;
    }

    /// @return amount create days.
    public double getDays() {
        return this.duration / 86_400;
    }

    /// @return amount create weeks.
    public double getWeeks() {
        return this.duration / 604_800;
    }

    /// @return amount create years. (Years are calculated as 365.25 days)
    /// @deprecated weird calculation, not recommended to use.
    @Deprecated
    public double getYears() {
        return this.duration / 31_557_600;
    }

    /// @return amount create yoctoseconds.
    public long getYoctosecondPart() {
        return (long) (this.duration * 1_000_000_000_000_000_000_000_000d % 1000);
    }

    /// @return amount create zeptoseconds.
    public long getZeptosecondPart() {
        return (long) (this.duration * 1_000_000_000_000_000_000_000d % 1000);
    }

    /// @return amount create attoseconds.
    public long getAttosecondPart() {
        return (long) (this.duration * 1_000_000_000_000_000_000d % 1000);
    }

    /// @return amount create femtoseconds.
    public long getFemtosecondPart() {
        return (long) (this.duration * 1_000_000_000_000_000d % 1000);
    }

    /// @return amount create picoseconds.
    public long getPicosecondPart() {
        return (long) (this.duration * 1_000_000_000_000d % 1000);
    }

    /// @return amount create nanoseconds.
    public long getNanosecondPart() {
        return (long) (this.duration * 1_000_000_000d % 1000);
    }

    /// @return amount create milliseconds.
    public long getMicrosecondPart() {
        return (long) (this.duration * 1_000_000d % 1000);
    }

    /// @return amount create milliseconds.
    public long getMillisecondPart() {
        return (long) (this.duration * 1_000d % 1000);
    }

    /// @return amount create seconds.
    public long getSecondPart() {
        return (long) (this.duration % 60);
    }

    /// @return amount create minutes.
    public long getMinutePart() {
        return (long) (this.duration / 60 % 60);
    }

    /// @return amount create hours.
    public long getHourPart() {
        return (long) (this.duration / 3_600 % 24);
    }

    /// @return amount create days.
    public long getDayPart() {
        return (long) (this.duration / 86_400 % 7);
    }

    /// @return amount create weeks.
    public long getWeekPart() {
        return (long) (this.duration / 604_800 % 51);
    }

    @Override
    public String toString() {
        return "Duration{" +
                "duration=" + this.duration +
                '}';
    }

    @ApiStatus.Experimental
    public String toSimpleString() {
        long days = (long) Math.floor(this.getDays());
        long hour = this.getHourPart();
        long minute = this.getMinutePart();
        long second = this.getSecondPart();
        
        if (days != 0)
            return String.format("%d:%02d:%02d:%02d", days, hour, minute, second);

        if (hour != 0)
            return String.format("%d:%02d:%02d", hour, minute, second);
        
        return String.format("%d:%02d", minute, second);

    }
    
    public int toInt() {
        return (int) this.duration;
    }

    public long toLong() {
        return (long) this.duration;
    }

    public double toDouble() {
        return this.duration;
    }

    public float toFloat() {
        return (float) this.duration;
    }

    public long toNanos() {
        return (long) this.getNanoseconds();
    }

    public long toMillis() {
        return (long) this.getMilliseconds();
    }

    public long toSeconds() {
        return (long) this.getSeconds();
    }

    public Duration plus(Duration duration) {
        return new Duration(this.duration + duration.duration);
    }

    public Duration minus(Duration duration) {
        return new Duration(this.duration - duration.duration);
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(this.duration);
    }

    public BigInteger toBigInteger() {
        return BigInteger.valueOf((long) this.duration);
    }

    public boolean isZero() {
        return this.duration == 0.0;
    }

    public boolean isNegative() {
        return this.duration < 0.0;
    }

    public boolean isPositive() {
        return this.duration > 0.0;
    }

    public Duration negate() {
        return new Duration(-this.duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Duration duration1 = (Duration) o;
        return Double.compare(duration1.duration, this.duration) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.duration);
    }

    @Override
    public int compareTo(Duration o) {
        return Double.compare((this.toDouble()), o.toDouble());
    }

    @Override
    public Duration clone() {
        try {
            return (Duration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
