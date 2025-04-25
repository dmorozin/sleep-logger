package com.noom.interview.fullstack.sleep.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepDateTimeUtilsTest {

    @Test
    void testGetFormattedMonthDay() {
        LocalDate date = LocalDate.of(2025, 4, 1);
        String formattedDate = SleepDateTimeUtils.getFormattedMonthDay(date, "MMMM");
        assertEquals("April, 1st", formattedDate);

        date = LocalDate.of(2025, 4, 2);
        formattedDate = SleepDateTimeUtils.getFormattedMonthDay(date, "MMMM");
        assertEquals("April, 2nd", formattedDate);

        date = LocalDate.of(2025, 9, 3);
        formattedDate = SleepDateTimeUtils.getFormattedMonthDay(date, "MMM");
        assertEquals("Sep, 3rd", formattedDate);

        date = LocalDate.of(2025, 10, 11);
        formattedDate = SleepDateTimeUtils.getFormattedMonthDay(date,"MMM");
        assertEquals("Oct, 11th", formattedDate);
    }

    @Test
    void testGetTimeInterval() {
        LocalTime startTime = LocalTime.of(22, 30);
        LocalTime endTime = LocalTime.of(6, 30);
        String interval = SleepDateTimeUtils.getTimeInterval(startTime, endTime);
        assertEquals("10:30 pm - 6:30 am", interval);
    }

    @Test
    void testGetFormattedTime() {
        long totalSeconds = 3660L;
        String formattedTime = SleepDateTimeUtils.getFormattedTime(totalSeconds);
        assertEquals("1 h 1 min", formattedTime);

        totalSeconds = 5400L;
        formattedTime = SleepDateTimeUtils.getFormattedTime(totalSeconds);
        assertEquals("1 h 30 min", formattedTime);

        totalSeconds = 7260L;
        formattedTime = SleepDateTimeUtils.getFormattedTime(totalSeconds);
        assertEquals("2 h 1 min", formattedTime);

        totalSeconds = 3600L;
        formattedTime = SleepDateTimeUtils.getFormattedTime(totalSeconds);
        assertEquals("1 h 0 min", formattedTime);
    }
}
