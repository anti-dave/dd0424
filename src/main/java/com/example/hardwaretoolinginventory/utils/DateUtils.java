package com.example.hardwaretoolinginventory.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        LocalDate julyFourthHoliday = calcJulyFourthHolidayForYear(date.getYear());
        LocalDate laborDayHoliday = LocalDate.of(date.getYear(), 9, 1)
            .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        return date.equals(julyFourthHoliday) || date.equals(laborDayHoliday);
    }

    public static LocalDate calcJulyFourthHolidayForYear(Integer year) {
        LocalDate julyFourthHoliday = null;
        LocalDate julyFourth = LocalDate.of(year, 7, 4);

        if(isWeekend(julyFourth)) {
            if(julyFourth.getDayOfWeek() == DayOfWeek.SATURDAY)
                julyFourthHoliday = julyFourth.minusDays(1);
            
            if(julyFourth.getDayOfWeek() == DayOfWeek.SUNDAY)
                julyFourthHoliday = julyFourth.plusDays(1);
        }
        return julyFourthHoliday;
    }
}
