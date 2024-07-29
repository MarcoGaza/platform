package cn.econets.blossom.module.trade.enums.brokerage;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Commission withdrawal type enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BrokerageWithdrawTypeEnum implements IntArrayValuable {

    WALLET(1, "Wallet"),
    BANK(2, "Bank card"),
    WECHAT(3, "WeChat"),
    ALIPAY(4, "Alipay"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BrokerageWithdrawTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
