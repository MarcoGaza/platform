package cn.econets.blossom.framework.operatelog.core.service;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Operation log
 *
 */
@Data
public class OperateLog {

    /**
     * Link tracking number
     */
    private String traceId;

    /**
     * User Number
     */
    private Long userId;
    /**
     * User Type
     */
    private Integer userType;

    /**
     * Operation module
     */
    private String module;

    /**
     * Operation name
     */
    private String name;

    /**
     * Operation Category
     */
    private Integer type;

    /**
     * Operation details
     */
    private String content;

    /**
     * Extended fields
     */
    private Map<String, Object> exts;

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
     * Browser UserAgent
     */
    private String userAgent;

    /**
     * Java Method name
     */
    private String javaMethod;

    /**
     * Java Method parameters
     */
    private String javaMethodArgs;

    /**
     * Start time
     */
    private LocalDateTime startTime;

    /**
     * Execution duration，Unit：milliseconds
     */
    private Integer duration;

    /**
     * Result code
     */
    private Integer resultCode;

    /**
     * Result prompt
     */
    private String resultMsg;

    /**
     * Result data
     */
    private String resultData;

}
