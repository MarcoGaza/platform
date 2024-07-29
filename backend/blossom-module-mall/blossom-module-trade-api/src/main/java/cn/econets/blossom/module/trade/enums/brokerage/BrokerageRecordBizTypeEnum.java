package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Commission record business type enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageRecordBizTypeEnum implements IntArrayValuable {

    ORDER(1, "Get promotion commission", "Get promotion commission {}", true),
    WITHDRAW(2, "Withdrawal Application", "Deduction of commission for withdrawal application {}", false),
    WITHDRAW_REJECT(3, "Withdrawal application rejected", "Withdrawal application rejected，Refund commission {}", true),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageRecordBizTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Title
     */
    private final String title;
    /**
     * Description
     */
    private final String description;
    /**
     * Whether to increase commission
     */
    private final boolean add;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
