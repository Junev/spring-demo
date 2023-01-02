package com.example.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TimePractice {
    public static void main(String[] args) {
        System.out.println("321");
        LocalDate ld1 = LocalDate.of(2022, 12, 31);
        int ld1Year = ld1.getYear();
        Month ld1Month = ld1.getMonth();
        int ld1DayOfMonth = ld1.getDayOfMonth();
        int ld1MonthValue = ld1Month.getValue();

        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 31, 16, 17, 45);

        final LocalDateTime localDateTime = ldt1.withHour(1);
        String dateStr = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String dateStr1 = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("123");
    }
}
