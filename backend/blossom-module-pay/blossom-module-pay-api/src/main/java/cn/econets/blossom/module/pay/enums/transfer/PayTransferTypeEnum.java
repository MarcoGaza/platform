package cn.econets.blossom.module.pay.enums.transfer;

import cn.hutool.core.util.ArrayUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Transfer type enumeration
 *
 *
 */
@AllArgsConstructor
@Getter
public enum PayTransferTypeEnum implements IntArrayValuable {

    ALIPAY_BALANCE(1, "Alipay balance"),
    WX_BALANCE(2, "WeChat balance"),
    BANK_CARD(3, "Bank card"),
    WALLET_BALANCE(4, "Wallet Balance");

    public interface WxPay {
    }

    public interface Alipay {
    }

    private final Integer type;
    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PayTransferTypeEnum::getType).toArray();

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static PayTransferTypeEnum typeOf(Integer type) {
        return ArrayUtil.firstMatch(item -> item.getType().equals(type), values());
    }

}
