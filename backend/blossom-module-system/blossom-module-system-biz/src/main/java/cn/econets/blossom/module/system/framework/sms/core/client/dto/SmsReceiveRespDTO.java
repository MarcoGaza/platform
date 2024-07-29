package cn.econets.blossom.module.system.framework.sms.core.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Message received Response DTO
 *
 */
@Data
public class SmsReceiveRespDTO {

    /**
     * Whether the reception was successful
     */
    private Boolean success;
    /**
     * API The encoding of the received result
     */
    private String errorCode;
    /**
     * API Description of receiving results
     */
    private String errorMsg;

    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * User receiving time
     */
    private LocalDateTime receiveTime;

    /**
     * SMS API Send the returned sequence number
     */
    private String serialNo;
    /**
     * SMS log number
     *
     * Corresponding SysSmsLogDO Number
     */
    private Long logId;

}
