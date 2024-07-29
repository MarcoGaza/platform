package cn.econets.blossom.module.crm.enums.customer;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * CRM Customer Level
 *
 */
@Getter
@AllArgsConstructor
public enum CrmCustomerLevelEnum implements IntArrayValuable {

    IMPORTANT(1, "A（Key Customers）"),
    GENERAL(2, "B（Ordinary Customer）"),
    LOW_PRIORITY(3, "C（Non-priority customers）");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmCustomerLevelEnum::getLevel).toArray();

    /**
     * Status
     */
    private final Integer level;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
