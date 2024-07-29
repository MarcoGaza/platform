package cn.econets.blossom.module.crm.enums.customer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * CRM Customer restriction configuration rule type
 *
 */
@Getter
@AllArgsConstructor
public enum CrmCustomerLimitConfigTypeEnum implements IntArrayValuable {

    /**
     * Limited number of customers
     */
    CUSTOMER_OWNER_LIMIT(1, "Limited number of customers"),
    /**
     * Lock the number of customers
     */
    CUSTOMER_LOCK_LIMIT(2, "Lock the number of customers"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmCustomerLimitConfigTypeEnum::getType).toArray();

    /**
     * Status
     */
    private final Integer type;
    /**
     * Status name
     */
    private final String name;

    public static String getNameByType(Integer type) {
        CrmCustomerLimitConfigTypeEnum typeEnum = CollUtil.findOne(CollUtil.newArrayList(CrmCustomerLimitConfigTypeEnum.values()),
                item -> ObjUtil.equal(item.type, type));
        return typeEnum == null ? null : typeEnum.getName();
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
