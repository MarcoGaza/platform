package cn.econets.blossom.module.promotion.enums.combination;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Group buying status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum CombinationRecordStatusEnum implements IntArrayValuable {

    IN_PROGRESS(0, "In progress"),
    SUCCESS(1, "Group buying successful"),
    FAILED(2, "Group buying failed");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CombinationRecordStatusEnum::getStatus).toArray();

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

    public static boolean isSuccess(Integer status) {
        return ObjectUtil.equal(status, SUCCESS.getStatus());
    }

    public static boolean isInProgress(Integer status) {
        return ObjectUtil.equal(status, IN_PROGRESS.getStatus());
    }

    public static boolean isFailed(Integer status) {
        return ObjectUtil.equal(status, FAILED.getStatus());
    }

}
