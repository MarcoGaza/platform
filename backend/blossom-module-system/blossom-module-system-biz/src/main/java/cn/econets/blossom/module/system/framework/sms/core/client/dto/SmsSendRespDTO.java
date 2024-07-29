package cn.econets.blossom.module.system.framework.sms.core.client.dto;

import lombok.Data;

/**
 * Send SMS Response DTO
 *
 */
@Data
public class SmsSendRespDTO {

    /**
     * Whether successful
     */
    private Boolean success;

    /**
     * API Request number
     */
    private String apiRequestId;

    // ==================== Fields when successful ====================

    /**
     * SMS API Send the returned sequence number
     */
    private String serialNo;

    // ==================== Failure field ====================

    /**
     * API Return error code
     *
     * Because the third-party error code may be a string，So use String Type
     */
    private String apiCode;
    /**
     * API Return prompt
     */
    private String apiMsg;

}
