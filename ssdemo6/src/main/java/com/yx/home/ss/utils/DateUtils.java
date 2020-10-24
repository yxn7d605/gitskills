package com.yx.home.ss.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Date.from(zonedDateTime.toInstant());
    }

    public static Date addMinus(Date date, int n) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        localDateTime = localDateTime.plusMinutes(n);

        return localDateTime2Date(localDateTime);
    }

    public static LocalDateTime hoursAddForNow(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.plusHours(n);
    }

    public static LocalDateTime minusAddForNow(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.plusMinutes(n);
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime;
    }

    public static String formatDate(Date date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = date2LocalDateTime(date);

        return dateTimeFormatter.format(localDateTime);
    }

    public static LocalDateTime todayEnd() {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        return todayEnd;
    }

    public static long todayEndTimeMillis() {
        LocalDateTime todayEnd = todayEnd();

        return todayEnd.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    public static LocalDateTime todayStart() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        return todayStart;
    }

    public static long todayStartTimeMillis() {
        LocalDateTime todayStart = todayStart();

        return todayStart.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    public static boolean isDateExpired(Date date) {
        Date now = new Date();

        return now.compareTo(date) > 0 ? true : false;
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date dateAdd = DateUtils.addMinus(date, -1);
        boolean result = DateUtils.isDateExpired(dateAdd);
        System.out.println(result);
    }
}
