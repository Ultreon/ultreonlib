package dev.ultreon.mods.lib.datetime;

import dev.ultreon.mods.lib.datetime.exceptions.DateTimeError;
import dev.ultreon.mods.lib.datetime.exceptions.DateTimeException;

import java.io.Serializable;
import java.time.*;

public class DateTime implements Comparable<DateTime>, Serializable, Cloneable {
    private static final Duration DURATION = new Duration(0.0d);
    private int hour;
    private int minute;
    private int second;

    private int day;
    private int year;
    private Month month;
    private int nano;

    public static DateTime current() {
        LocalDateTime dateTime = LocalDateTime.now();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();

        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int year = dateTime.getYear();
        int nano = dateTime.getNano();

        return new DateTime(day, month, year, hour, minute, second, nano);
    }

    public static boolean isLeapYear(int year) {
        return (year & 3) == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public DateTime(DateTime dateTime) {
        this(dateTime.day, dateTime.month, dateTime.year, dateTime.hour, dateTime.minute, dateTime.second, dateTime.nano);
    }

    public DateTime(Date date, Time time) {
        this(date.getDay(), date.getMonth(), date.getYear(), time.getHour(), time.getMinute(), time.getSecond());
    }

    public DateTime(int day, int month, int year, int hour, int minute, int second) {
        this(day, Month.from(month), year, hour, minute, second);
    }

    public DateTime(int day, Month month, int year, int hour, int minute, int second) {
        this(day, month, year, hour, minute, second, 0);
    }

    public DateTime(int day, int month, int year, int hour, int minute, int second, int nano) {
        this(day, Month.from(month), year, hour, minute, second);
    }

    public DateTime(int day, Month month, int year, int hour, int minute, int second, int nano) {
        if (second < 0 || second > 59) throw new DateTimeException("Second must be between 0 and 59");
        if (minute < 0 || minute > 59) throw new DateTimeException("Minute must be between 0 and 59");
        if (hour < 0 || hour > 59) throw new DateTimeException("Minute must be between 0 and 59");
        if (nano < 0 || nano > 999_999_999) throw new DateTimeException("Nano must be between 0 and 999'999'999");
        Date.checkDayOfMonth(day, month, year);

        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.day = day;
        this.month = month;
        this.year = year;
        this.nano = nano;
    }

    public static DateTime ofEpochSecond(long second, ZoneOffset offset) {
        return ofLocalDateTime(LocalDateTime.ofEpochSecond(second, 0, offset));
    }

    public static DateTime ofEpochMilli(long milli, ZoneOffset offset) {
        return ofLocalDateTime(LocalDateTime.ofEpochSecond(milli / 1000, (int) ((milli % 1000) * 1_000_000), offset));
    }

    public static DateTime ofEpochNano(long nano, ZoneOffset offset) {
        return ofLocalDateTime(LocalDateTime.ofEpochSecond(nano / 1_000_000_000, (int) ((nano % 1_000_000_000)), offset));
    }

    public static DateTime ofLocalDateTime(LocalDateTime ldt) {
        return new DateTime(ldt.getDayOfMonth(), ldt.getMonthValue(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }

    public static DateTime ofInstant(Instant lt, ZoneOffset offset) {
        return ofEpochMilli(lt.toEpochMilli(), offset);
    }

    /// Return flag meaning the object is between time1 and time2.
    ///
    /// @param lo low value.
    /// @param hi high value.
    /// @return true if the object is between time1 and time2.
    /// @throws NullPointerException if ‘lo’ is higher than ‘hi’.
    public static boolean isBetween(DateTime lo, DateTime hi) {
        if (lo.toEpochNano() > hi.toEpochNano()) throw new NullPointerException("‘lo’ is higher than ‘hi’");

        return lo.toEpochNano() <= hi.toEpochNano() && hi.toEpochNano() >= lo.toEpochNano();
    }

    @Deprecated(forRemoval = true)
    public long toEpochSeconds() {
        return this.toEpochSecond();
    }

    public long toEpochSecond() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(ZoneOffset.ofTotalSeconds(0));
    }

    public long toEpochMilli() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(ZoneOffset.ofTotalSeconds(0)) + this.nano / 1_000_000;
    }

    public long toEpochNano() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(ZoneOffset.ofTotalSeconds(0)) + this.nano;
    }

    public long toEpochSecond(ZoneOffset offset) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(offset);
    }

    public long toEpochMilli(ZoneOffset offset) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(offset) + this.nano / 1_000_000;
    }

    public long toEpochNano(ZoneOffset offset) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(this.year, this.month.getIndex(), this.day), LocalTime.of(this.hour, this.minute, this.second));
        return localDateTime.toEpochSecond(offset) + this.nano;
    }

    /// Value between 0 and 23
    ///
    /// @return the hour.
    public int getHour() {
        return this.hour;
    }

    /// Value between 0 and 23
    ///
    /// @param hour value to set.
    public void setHour(int hour) {
        if (hour < 0 || hour > 23) throw new DateTimeException("Hour must be between 0 and 23");
        this.hour = hour;
    }

    /// Value between 0 and 59.
    ///
    /// @return the minute.
    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) throw new DateTimeException("Minute must be between 0 and 59");
        this.minute = minute;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second) {
        if (second < 0 || second > 59) throw new DateTimeException("Second must be between 0 and 59");
        this.second = second;
    }

    public int getNano() {
        return this.nano;
    }

    public void setNano(int nano) {
        if (nano < 0 || nano > 999_999_999) throw new DateTimeException("Nano must be between 0 and 999'999'999");
        this.nano = nano;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        int days = this.getMonth().getDays(this.year);
        if (this.minute < 1 || this.minute > days) throw new DateTimeException("Minute must be between 1 and " + days);
        this.day = day;
    }

    public int getMonthIndex() {
        return this.month.getIndex();
    }

    public void setMonthIndex(int index) {
        this.month = Month.from(index);
    }

    public Month getMonth() {
        return this.month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Time getTime() {
        return new Time(this.hour, this.minute, this.second, this.nano);
    }

    public void setTime(Time time) {
        this.hour = time.getHour();
        this.minute = time.getMinute();
        this.second = time.getSecond();
        this.nano = time.getNano();
    }

    public Date getDate() {
        return new Date(this.day, this.month, this.year);
    }

    public void getDate(Date date) {
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
    }

    @Deprecated(forRemoval = true)
    public Duration getDuration() {
        return DURATION;
    }


    public DayPeriod getDayPeriod() {
        Time time = this.getTime();
        if (DayPeriod.NIGHT.isWithin(time)) return DayPeriod.NIGHT;
        if (DayPeriod.MORNING.isWithin(time)) return DayPeriod.MORNING;
        if (DayPeriod.AFTERNOON.isWithin(time)) return DayPeriod.AFTERNOON;
        if (DayPeriod.NIGHT.isWithin(time)) return DayPeriod.NIGHT;
        throw new DateTimeError("Can't find valid day period.");
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(this.hour, this.minute, this.second, this.nano);
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(this.year, this.month.getIndex(), this.day);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.year, this.month.getIndex(), this.day, this.hour, this.minute, this.second, this.nano);
    }

    @Override
    public int compareTo(DateTime o) {
        return Long.compare(this.toEpochNano(), o.toEpochNano());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.toEpochNano());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return this.toEpochNano() == ((DateTime) o).toEpochNano();
    }

    @Override
    public DateTime clone() {
        try {
            return (DateTime) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
