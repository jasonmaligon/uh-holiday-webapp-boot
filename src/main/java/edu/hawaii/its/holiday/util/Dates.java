package edu.hawaii.its.holiday.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public final class Dates {

    public static final String DATE_FORMAT = "MMMM dd, yyyy, EEEE";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    // Private constructor; prevent instantiation.
    private Dates() {
        // Empty.
    }

    public static long compareDates(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    public static LocalDate newLocalDate() {
        return LocalDate.now();
    }

    public static LocalDate newLocalDate(int year, Month month, int day) {
        return LocalDate.of(year, month, day);
    }

    public static Month month(LocalDate date) {
        return date.getMonth();
    }

    public static LocalDate firstOfYear(LocalDate date) {
        return newLocalDate(date.getYear(), Month.JANUARY, 1);
    }

    public static LocalDate firstOfNextYear(LocalDate date) {
        return newLocalDate(date.getYear() + 1, Month.JANUARY, 1);
    }

    public static LocalDate firstOfPreviousYear(LocalDate date) {
        return newLocalDate(date.getYear() - 1, Month.JANUARY, 1);
    }

    public static LocalDate firstOfMonth(Month month, int year) {
        return firstDateOfMonth(month, year);
    }

    public static LocalDate firstDateOfMonth(Month month, int year) {
        return newLocalDate(year, month, 1);
    }

    public static LocalDate firstOfNextMonth(LocalDate date) {
        return newLocalDate(date.getYear(), date.getMonth(), 1).plusMonths(1);
    }

    public static LocalDate previousSunday(LocalDate date) {
        return date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    }

    public static LocalDate fromOffset(LocalDate date, int days) {
        return date.plusDays(days);
    }

    public static int lastDayOfMonth(Month month, int year) {
        return LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .getDayOfMonth();
    }

    public static LocalDate lastDateOfMonth(Month month, int year) {
        return LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate firstDateOfYear(int year) {
        return newLocalDate(year, Month.JANUARY, 1);
    }

    public static LocalDate lastDateOfYear(int year) {
        return newLocalDate(year, Month.DECEMBER, 31);
    }

    public static LocalDate lastDateOfYear(LocalDate date) {
        return lastDateOfYear(date.getYear());
    }

    public static int dayOfMonth(LocalDate date) {
        return date.getDayOfMonth();
    }

    public static String formatDate(LocalDateTime dateTime, String formatStr) {
        if (dateTime == null) {
            return "";
        }
        String result = dateTime.toString();

        try {
            result = dateTime.format(DateTimeFormatter.ofPattern(formatStr));
        } catch (Exception e) {
            // Ignored.
        }

        return result;
    }

    public static String formatDate(LocalDate date, String formatStr) {
        if (date == null) {
            return "";
        }
        String result = date.toString();

        try {
            result = date.format(DateTimeFormatter.ofPattern(formatStr));
        } catch (Exception e) {
            // Ignored.
        }

        return result;
    }

    // Not sure we really need this method.
    public static String formatDate(LocalDate date) {
        return formatDate(date, "MM/dd/yyyy");
    }

    // Not sure we really need this method.
    public static String formatDate(Date date) {
        return formatDate(toLocalDate(date), "MM/dd/yyyy");
    }

    public static int currentYear() {
        return LocalDate.now().getYear();
    }

    public static int yearOfDate(LocalDate date) {
        return date.getYear();
    }

    public static DayOfWeek dayOfWeek(LocalDate date) {
        return date.getDayOfWeek();
    }

    private static ZoneId zoneId() {
        return ZoneId.systemDefault();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        Instant instant = new Date(date.getTime()).toInstant();
        ZonedDateTime zoneDateTime = instant.atZone(zoneId());
        return zoneDateTime.toLocalDate();
    }

    public static LocalDate toLocalDate(String s) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(s, formatter);
        } catch (Exception e) {
            // Ignored.
        }
        return localDate;
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(zoneId()).toInstant());
    }

    public static LocalDate toLocalDate(String s, String format) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(s, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            // Ignored.
        }
        return localDate;
    }


}
