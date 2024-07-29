package cn.econets.blossom.module.crm.enums.product;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * CRM Product Status
 *
 */
@Getter
@AllArgsConstructor
public enum CrmProductStatusEnum implements IntArrayValuable {

    DISABLE(0, "Off the shelf"),
    ENABLE(1, "On the shelf");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmProductStatusEnum::getStatus).toArray();

    /**
     * Status
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
