package cn.econets.blossom.module.system.framework.sms.core.client.dto;

import cn.econets.blossom.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import lombok.Data;

/**
 * SMS template Response DTO
 *
 */
@Data
public class SmsTemplateRespDTO {

    /**
     * Template number
     */
    private String id;
    /**
     * SMS content
     */
    private String content;
    /**
     * Review status
     *
     * Enumeration {@link SmsTemplateAuditStatusEnum}
     */
    private Integer auditStatus;
    /**
     * Reason for failure to pass the review
     */
    private String auditReason;

}
