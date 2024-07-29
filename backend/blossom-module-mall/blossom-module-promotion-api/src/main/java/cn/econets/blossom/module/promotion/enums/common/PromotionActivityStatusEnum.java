package cn.econets.blossom.module.promotion.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

// TODO Weaken this state
/**
 * Promotion status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum PromotionActivityStatusEnum implements IntArrayValuable {

    WAIT(10, "Not started yet"),
    RUN(20, "In progress"),
    END(30, "Ended"),
    CLOSE(40, "Closed");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PromotionActivityStatusEnum::getStatus).toArray();

    /**
     * Status value
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
