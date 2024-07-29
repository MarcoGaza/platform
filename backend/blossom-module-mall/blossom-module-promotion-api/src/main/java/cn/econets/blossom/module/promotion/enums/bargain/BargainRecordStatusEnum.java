package cn.econets.blossom.module.promotion.enums.bargain;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Bargaining record status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BargainRecordStatusEnum implements IntArrayValuable {

    IN_PROGRESS(1, "Bargaining"),
    SUCCESS(2, "Bargaining successful"),
    FAILED(3, "Bargaining failed"), // When the activity expires，All expired bargains will be automatically set as expired
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BargainRecordStatusEnum::getStatus).toArray();

    /**
     * Value
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
