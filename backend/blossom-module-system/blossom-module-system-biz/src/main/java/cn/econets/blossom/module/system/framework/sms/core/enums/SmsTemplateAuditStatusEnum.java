package cn.econets.blossom.module.system.framework.sms.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS template review status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum SmsTemplateAuditStatusEnum {

    CHECKING(1),
    SUCCESS(2),
    FAIL(3);

    private final Integer status;

}
