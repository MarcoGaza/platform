package cn.econets.blossom.module.promotion.util;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;

import java.time.LocalDateTime;

/**
 * Activity Tools
 *
 */
public class PromotionUtils {

    /**
     * According to time，Calculate activity status
     *
     * @param endTime End time
     * @return Activity Status
     */
    public static Integer calculateActivityStatus(LocalDateTime endTime) {
        return LocalDateTimeUtils.beforeNow(endTime) ? CommonStatusEnum.DISABLE.getStatus() : CommonStatusEnum.ENABLE.getStatus();
    }

}
