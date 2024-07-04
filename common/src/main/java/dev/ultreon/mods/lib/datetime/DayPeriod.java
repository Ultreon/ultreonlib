package dev.ultreon.mods.lib.datetime;

public enum DayPeriod {
    NIGHT(new Time(0, 0, 0), new Time(6, 0, 0)),
    MORNING(new Time(6, 0, 0), new Time(12, 0, 0)),
    AFTERNOON(new Time(12, 0, 0), new Time(18, 0, 0)),
    EVENING(new Time(18, 0, 0), new Time(0, 0, 0)),
    ;

    private final Time start;
    private final Time end;

    DayPeriod(Time startInclusive, Time endExclusive) {
        this.start = startInclusive;
        this.end = endExclusive;
    }

    /**
     * Time the day period begins.<br>
     *
     * @return the start time, note that the returned time is inclusive.
     */
    public Time getStart() {
        return this.start;
    }

    /**
     * Time the day period has ended.<br>
     *
     * @return the end time, note that the returned time is exclusive.
     */
    public Time getEnd() {
        return this.end;
    }

    public boolean isWithin(Time time) {
        switch (this) {
            case EVENING:
                return time.isAfterOrEqual(this.start);
            case NIGHT:
                return time.isBefore(this.end);
            default:
                return time.isAfterOrEqual(this.start) && time.isBefore(this.end);
        }
    }
}
