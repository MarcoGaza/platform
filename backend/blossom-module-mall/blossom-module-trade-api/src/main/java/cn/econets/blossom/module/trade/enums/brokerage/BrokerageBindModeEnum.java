package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Distribution relationship binding mode enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageBindModeEnum implements IntArrayValuable {

    /**
     * As long as the user has no promoter，You can bind the distribution relationship at any time
     */
    ANYTIME(1, "First Binding"),
    /**
     * Only new users can bind promotion relationship when registering
     */
    REGISTER(2, "Registration Binding"),
    /**
     * Each scan will cover the code
     */
    OVERRIDE(3, "Override Binding"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageBindModeEnum::getMode).toArray();

    /**
     * Mode
     */
    private final Integer mode;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
