package cn.econets.blossom.module.system.api.logger.dto;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * System operation log Create Request DTO
 *
 */
@Data
public class OperateLogV2CreateReqDTO {

    /**
     * Link tracking number
     *
     * Generally speaking，Tracking number by link，You can access the log，Error log，Link tracking log，logger Print logs, etc.，Combined together，To debug。
     */
    private String traceId;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Properties，Or AdminUserDO of id Properties
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     *
     * Relationship {@link  UserTypeEnum}
     */
    @NotNull(message = "User type cannot be empty")
    private Integer userType;
    /**
     * Operation module type
     */
    @NotEmpty(message = "Operation module type cannot be empty")
    private String type;
    /**
     * Operation name
     */
    @NotEmpty(message = "Operation name cannot be empty")
    private String subType;
    /**
     * Operation module business number
     */
    @NotNull(message = "The operation module business number cannot be empty")
    private Long bizId;
    /**
     * Operation content，Record the details of the entire operation
     * For example，Change the number to 1 User information，Change gender from male to female，Change the name from Yudao to Yuanma。
     */
    @NotEmpty(message = "The operation content cannot be empty")
    private String action;
    /**
     * Extended fields，Some complicated business，Some fields need to be recorded ( JSON Format )
     * For example，Record order number，{ orderId: "1"}
     */
    private String extra;

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
     * Browser UA
     */
    @NotEmpty(message = "Browser UA Cannot be empty")
    private String userAgent;

}
