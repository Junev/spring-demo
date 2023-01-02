package com.example.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

public class TimePractice {
    public static void main(String[] args) {
        basicLocalDate();

        machineTime();

        durationAndPeriod();

        manipulateDate();

        dateTimeFormatter();

        zonedDateTime();
    }

    private static void zonedDateTime() {
        ZoneId zoneId1 = ZoneId.of("Europe/Rome");
        ZoneId zoneId2 = TimeZone.getDefault().toZoneId();
        LocalDate date1 = LocalDate.of(2023, 1, 2);
        ZonedDateTime zonedDt1 = date1.atStartOfDay(zoneId1);
        ZonedDateTime zonedDt2 = date1.atStartOfDay(zoneId2);

        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 31, 16, 17, 45);
        ZonedDateTime zonedDateTime = ldt1.atZone(zoneId1);
        ZonedDateTime zonedDateTime1 = ldt1.atZone(zoneId2);

        String timeStr1 = zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String timeStr2 = zonedDateTime1.format(DateTimeFormatter.ISO_DATE_TIME);

        Instant now = Instant.now();
        ZonedDateTime zonedDateTime2 = now.atZone(zoneId1);
        ZonedDateTime zonedDateTime3 = now.atZone(zoneId2);
    }

    private static void dateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr1 = LocalDate.now().format(dateTimeFormatter);
        LocalDate date1 = LocalDate.parse("01/02/2023", dateTimeFormatter);
    }

    private static void manipulateDate() {
        LocalDate nextMonth1 = LocalDate.now().plus(Period.ofMonths(1));

        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 31, 16, 17, 45);

        final LocalDateTime localDateTime = ldt1.withHour(1);
        String dateStr = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String dateStr1 = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);


    }

    private static void durationAndPeriod() {
        // duration 主要用于秒和纳秒衡量时间的长短
        Duration d1 = Duration.ofMinutes(3);

        // period 主要用于年月日的方式
        Period p1 = Period.ofDays(3);
    }

    private static void machineTime() {
        Instant instant1 = Instant.now();
    }

    // 使用LocalDate和LocalDateTime
    private static void basicLocalDate() {
        LocalDate ld1 = LocalDate.of(2022, 12, 31);
        int ld1Year = ld1.getYear();
        Month ld1Month = ld1.getMonth();
        int ld1DayOfMonth = ld1.getDayOfMonth();
        int ld1MonthValue = ld1Month.getValue();

        int ld1Year2 = ld1.get(ChronoField.YEAR);

        LocalDate date = LocalDate.parse("2023-01-02");

        // 无法解析时, 会抛出DateTimeParseException
//        LocalDate date1 = LocalDate.parse("20230102");

        LocalTime time1 = LocalTime.parse("15:23:00");
    }
}
