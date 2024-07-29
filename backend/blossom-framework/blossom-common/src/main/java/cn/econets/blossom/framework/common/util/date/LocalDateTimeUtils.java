package cn.econets.blossom.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Time Tools，Used for {@link LocalDateTime}
 *
 */
public class LocalDateTimeUtils {

    /**
     * Empty LocalDateTime Object，Mainly used for DB Default value of unique index
     */
    public static LocalDateTime EMPTY = buildTime(1970, 1, 1);

    public static LocalDateTime addTime(Duration duration) {
        return LocalDateTime.now().plus(duration);
    }

    public static LocalDateTime minusTime(Duration duration) {
        return LocalDateTime.now().minus(duration);
    }

    public static boolean beforeNow(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }

    public static boolean afterNow(LocalDateTime date) {
        return date.isAfter(LocalDateTime.now());
    }

    /**
     * Create a specified time
     *
     * @param year  Year
     * @param mouth Moon
     * @param day   Japanese
     * @return Specify time
     */
    public static LocalDateTime buildTime(int year, int mouth, int day) {
        return LocalDateTime.of(year, mouth, day, 0, 0, 0);
    }

    public static LocalDateTime[] buildBetweenTime(int year1, int mouth1, int day1,
                                                   int year2, int mouth2, int day2) {
        return new LocalDateTime[]{buildTime(year1, mouth1, day1), buildTime(year2, mouth2, day2)};
    }

    /**
     * Judge whether the current time is within the time range
     *
     * @param startTime Start time
     * @param endTime   End time
     * @return Yes
     */
    public static boolean isBetween(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        return LocalDateTimeUtil.isIn(LocalDateTime.now(), startTime, endTime);
    }

    /**
     * Judge whether the current time is within the time range
     *
     * @param startTime Start time
     * @param endTime   End time
     * @return Yes
     */
    public static boolean isBetween(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        LocalDate nowDate = LocalDate.now();
        return LocalDateTimeUtil.isIn(LocalDateTime.now(),
                LocalDateTime.of(nowDate, LocalTime.parse(startTime)),
                LocalDateTime.of(nowDate, LocalTime.parse(endTime)));
    }

    /**
     * Judge whether time periods overlap
     *
     * @param startTime1 Start time1
     * @param endTime1   End time1
     * @param startTime2 Start time2
     * @param endTime2   End time2
     * @return Overlap：true No overlap：false
     */
    public static boolean isOverlap(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2) {
        LocalDate nowDate = LocalDate.now();
        return LocalDateTimeUtil.isOverlap(LocalDateTime.of(nowDate, startTime1), LocalDateTime.of(nowDate, endTime1),
                LocalDateTime.of(nowDate, startTime2), LocalDateTime.of(nowDate, endTime2));
    }

    /**
     * Get the start time of the month in which the specified date falls
     * For example：2023-09-30 00:00:00,000
     *
     * @param date Date
     * @return Start of the month
     */
    public static LocalDateTime beginOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * Get the last time of the month in which the specified date is located
     * For example：2023-09-30 23:59:59,999
     *
     * @param date Date
     * @return End of the month
     */
    public static LocalDateTime endOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * Get the number of days that have passed since the specified date，If the specified date is after the current date，The result is negative
     *
     * @param dateTime Date
     * @return Difference in days
     */
    public static Long between(LocalDateTime dateTime) {
        return LocalDateTimeUtil.between(dateTime, LocalDateTime.now(), ChronoUnit.DAYS);
    }

}
