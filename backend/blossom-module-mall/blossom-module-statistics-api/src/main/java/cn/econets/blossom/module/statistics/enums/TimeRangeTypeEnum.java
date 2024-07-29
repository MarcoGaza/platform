package cn.econets.blossom.module.statistics.enums;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enumeration of time range types
 *
 */
@AllArgsConstructor
@Getter
public enum TimeRangeTypeEnum implements IntArrayValuable {

    /**
     * Sky
     */
    DAY(1),
    /**
     * Week
     */
    WEEK(7),
    /**
     * Moon
     */
    MONTH(30),
    /**
     * Year
     */
    YEAR(365),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TimeRangeTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
