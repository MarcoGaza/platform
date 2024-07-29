package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Commission record status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageRecordStatusEnum implements IntArrayValuable {

    WAIT_SETTLEMENT(0, "To be settled"),
    SETTLEMENT(1, "Settled"),
    CANCEL(2, "Cancelled"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageRecordStatusEnum::getStatus).toArray();

    /**
     * Status
     */
    private final Integer status;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
