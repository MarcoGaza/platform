package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Commission mode enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageEnabledConditionEnum implements IntArrayValuable {

    /**
     * All users can distribute
     */
    ALL(1, "Everyone distributes"),
    /**
     * Promoters can only be set manually in the background
     */
    ADMIN(2, "Designated distribution"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageEnabledConditionEnum::getCondition).toArray();

    /**
     * Mode
     */
    private final Integer condition;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
