package cn.econets.blossom.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Time Tools
 *
 */
public class DateUtils {

    /**
     * Time zone - Default
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * Convert seconds to milliseconds
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * Will LocalDateTime Convert to Date
     *
     * @param date LocalDateTime
     * @return LocalDateTime
     */
    public static Date of(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        // Combine this date and time with the time zone to create ZonedDateTime
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        // Local timeline LocalDateTime To the real-time timeline Instant Timestamp
        Instant instant = zonedDateTime.toInstant();
        // UTCTime(UTC Time,UTC + 00:00)Transfer to Beijing(Beijing,UTC + 8:00)Time
        return Date.from(instant);
    }

    /**
     * Will Date Convert to LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        // Convert to timestamp
        Instant instant = date.toInstant();
        // UTCTime(UTC Time,UTC + 00:00)Transfer to Beijing(Beijing,UTC + 8:00)Time
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(Date time) {
        return System.currentTimeMillis() > time.getTime();
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    public static long diff(Date endTime, Date startTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * Create a specified time
     *
     * @param year  Year
     * @param mouth Moon
     * @param day   Japanese
     * @return Specify time
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * Create a specified time
     *
     * @param year   Year
     * @param mouth  Moon
     * @param day    Japanese
     * @param hour   Hours
     * @param minute minutes
     * @param second seconds
     * @return Specify time
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // Under normal circumstances，All 0 milliseconds
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.isAfter(b) ? a : b;
    }

    /**
     * Calculate the date difference between the current time
     *
     * @param field  Calendar Field.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAYWait.
     * @param amount The difference in value
     * @return Calculated log
     */
    public static Date addDate(int field, int amount) {
        return addDate(null, field, amount);
    }

    /**
     * Calculate the date difference between the current time
     *
     * @param date   Set time
     * @param field  Calendar Field For example，{@link Calendar#DAY_OF_MONTH} Wait
     * @param amount The difference in value
     * @return Calculated log
     */
    public static Date addDate(Date date, int field, int amount) {
        if (amount == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * Is it today?
     *
     * @param date Date
     * @return Yes
     */
    public static boolean isToday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now());
    }

    /**
     * Whether it was yesterday
     *
     * @param date Date
     * @return Yes
     */
    public static boolean isYesterday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now().minusDays(1));
    }

}
