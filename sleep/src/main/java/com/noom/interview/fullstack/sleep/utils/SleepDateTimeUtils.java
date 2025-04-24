package com.noom.interview.fullstack.sleep.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepDateTimeUtils {

    public static String getFormattedMonthDay(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        String month = date.format(formatter);

        int dayOfMonth = date.getDayOfMonth();
        String day = dayOfMonth + getDaySuffix(dayOfMonth);

        return month + ", " + day;
    }

    public static String getTimeInterval(LocalTime startTime, LocalTime endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return startTime.format(timeFormatter) + " - " + endTime.format(timeFormatter);
    }

    public static String getFormattedTime(Long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        return hours + " h " + minutes + " min";
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }

        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
