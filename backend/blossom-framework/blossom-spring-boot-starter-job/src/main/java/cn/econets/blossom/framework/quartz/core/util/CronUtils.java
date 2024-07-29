package cn.econets.blossom.framework.quartz.core.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Quartz Cron Expression tool class
 *
 */
public class CronUtils {

    /**
     * Verification CRON Is the expression valid?
     *
     * @param cronExpression CRON Expression
     * @return Is it valid?
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * Based on CRON Expression，Get next n Time to satisfy execution
     *
     * @param cronExpression CRON Expression
     * @param n Quantity
     * @return Execution time that meets the conditions
     */
    public static List<LocalDateTime> getNextTimes(String cronExpression, int n) {
        // Get CronExpression Object
        CronExpression cron;
        try {
            cron = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        // Start calculation from now，n Those who meet the conditions
        Date now = new Date();
        List<LocalDateTime> nextTimes = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Date nextTime = cron.getNextValidTimeAfter(now);
            nextTimes.add(LocalDateTimeUtil.of(nextTime));
            // Switch now，The next trigger time；
            now = nextTime;
        }
        return nextTimes;
    }

}
