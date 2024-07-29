package cn.econets.blossom.module.product.enums.comment;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Product review approval status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum ProductCommentAuditStatusEnum implements IntArrayValuable {

    NONE(1, "Awaiting review"),
    APPROVE(2, "Approved"),
    REJECT(2, "Approval failed"),;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductCommentAuditStatusEnum::getStatus).toArray();

    /**
     * Approval Status
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
