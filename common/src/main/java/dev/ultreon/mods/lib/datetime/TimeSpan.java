package dev.ultreon.mods.lib.datetime;

import dev.ultreon.mods.lib.datetime.exceptions.InvalidOrderException;

import java.io.Serializable;

public class TimeSpan implements Serializable, Cloneable {
    private DateTime from;
    private DateTime to;

    public TimeSpan(DateTime from, DateTime to) {
        if (from.toEpochNano() > to.toEpochNano()) throw new InvalidOrderException("Parameter ‘from’ is later than ‘to’.");

        this.from = from;
        this.to = to;
    }

    public boolean contains(DateTime dateTime) {
        return DateTime.isBetween(this.from, this.to);
    }

    public Duration toDuration() {
        return Duration.ofNanoseconds(this.to.toEpochNano() - this.from.toEpochNano());
    }

    public DateTime getFrom() {
        return this.from;
    }

    public DateTime getTo() {
        return this.to;
    }

    public void setFrom(DateTime from) {
        this.from = from;
    }

    public void setTo(DateTime to) {
        this.to = to;
    }

    @Override
    public TimeSpan clone() {
        try {
            return (TimeSpan) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
