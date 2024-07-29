package cn.econets.blossom.module.system.api.logger.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Operation log creation Request DTO
 */
@Data
public class OperateLogCreateReqDTO {

    /**
     * Link tracking number
     */
    private String traceId;

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     */
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    /**
     * Operation module
     */
    @NotEmpty(message = "The operation module cannot be empty")
    private String module;

    /**
     * Operation name
     */
    @NotEmpty(message = "Operation name")
    private String name;

    /**
     * Operation Category
     */
    @NotNull(message = "Operation category cannot be empty")
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
    @NotEmpty(message = "The request method name cannot be empty")
    private String requestMethod;

    /**
     * Request address
     */
    @NotEmpty(message = "The request address cannot be empty")
    private String requestUrl;

    /**
     * User IP
     */
    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;

    /**
     * Browser UserAgent
     */
    @NotEmpty(message = "Browser UserAgent Cannot be empty")
    private String userAgent;

    /**
     * Java Method name
     */
    @NotEmpty(message = "Java The method name cannot be empty")
    private String javaMethod;

    /**
     * Java Method parameters
     */
    private String javaMethodArgs;

    /**
     * Start time
     */
    @NotNull(message = "Start time cannot be empty")
    private LocalDateTime startTime;

    /**
     * Execution duration，Unit：milliseconds
     */
    @NotNull(message = "Execution duration cannot be empty")
    private Integer duration;

    /**
     * Result code
     */
    @NotNull(message = "The result code cannot be empty")
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
