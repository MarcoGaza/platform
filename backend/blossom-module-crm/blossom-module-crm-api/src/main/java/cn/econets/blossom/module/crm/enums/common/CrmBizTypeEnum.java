package cn.econets.blossom.module.crm.enums.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * CRM Business type enumeration
 *
 */
@RequiredArgsConstructor
@Getter
public enum CrmBizTypeEnum implements IntArrayValuable {

    CRM_LEADS(1, "Clues"),
    CRM_CUSTOMER(2, "Customer"),
    CRM_CONTACT(3, "Contact Person"),
    CRM_BUSINESS(4, "Business Opportunities"),
    CRM_CONTRACT(5, "Contract"),
    CRM_PRODUCT(6, "Products"),
    CRM_RECEIVABLE(7, "Payback"),
    CRM_RECEIVABLE_PLAN(8, "Payment Refund Plan")
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmBizTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;

    public static String getNameByType(Integer type) {
        CrmBizTypeEnum typeEnum = CollUtil.findOne(CollUtil.newArrayList(CrmBizTypeEnum.values()),
                item -> ObjUtil.equal(item.type, type));
        return typeEnum == null ? null : typeEnum.getName();
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
