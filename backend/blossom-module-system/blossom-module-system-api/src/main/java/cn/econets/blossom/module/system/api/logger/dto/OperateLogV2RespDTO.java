package cn.econets.blossom.module.system.api.logger.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * System operation log Resp DTO
 *
 */
@Data
public class OperateLogV2RespDTO {

    /**
     * Link tracking number
     */
    private String traceId;
    /**
     * User Number
     */
    private Long userId;
    /**
     * User Name
     */
    private String userName;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * Operation module type
     */
    private String type;
    /**
     * Operation name
     */
    private String subType;
    /**
     * Operation module business number
     */
    private Long bizId;
    /**
     * Operation content
     */
    private String action;
    /**
     * Extended fields
     */
    private String extra;

    /**
     * Request method name
     */
    private String requestMethod;
    /**
     * Request address
     */
    private String requestUrl;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Browser UA
     */
    private String userAgent;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

}
