package cn.econets.blossom.module.crm.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * CRM Approval status
 *
 */
@RequiredArgsConstructor
@Getter
public enum CrmAuditStatusEnum implements IntArrayValuable {

    DRAFT(0, "Not submitted"),
    PROCESS(10, "Under review"),
    APPROVE(20, "Approved"),
	REJECT(30, "Review failed"),
    CANCEL(40, "Cancelled");

    private final Integer status;
    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmAuditStatusEnum::getStatus).toArray();

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
