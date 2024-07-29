package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

// TODO Withdrawal is now available，In entanglement；
/**
 * Commission withdrawal status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageWithdrawStatusEnum implements IntArrayValuable {

    AUDITING(0, "Under review"),
    AUDIT_SUCCESS(10, "Approved"),
    WITHDRAW_SUCCESS(11, "Withdrawal successful"),
    AUDIT_FAIL(20, "Review failed"),
    WITHDRAW_FAIL(21, "Withdrawal failed"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageWithdrawStatusEnum::getStatus).toArray();

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
