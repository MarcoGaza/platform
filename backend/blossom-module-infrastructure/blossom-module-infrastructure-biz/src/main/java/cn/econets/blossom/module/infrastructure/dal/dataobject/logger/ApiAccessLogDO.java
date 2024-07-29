package cn.econets.blossom.module.infrastructure.dal.dataobject.logger;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * API Access log
 *
 */
@TableName("infra_api_access_log")
@KeySequence(value = "infra_api_access_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiAccessLogDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Link tracking number
     *
     * Generally speaking，Tracking number by link，You can access the log，Error log，Link tracking log，logger Print logs, etc.，Combined together，To debug。
     */
    private String traceId;
    /**
     * User ID
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * Application name
     *
     * Currently reading `spring.application.name` Configuration item
     */
    private String applicationName;

    // ========== Request related fields ==========

    /**
     * Request method name
     */
    private String requestMethod;
    /**
     * Access address
     */
    private String requestUrl;
    /**
     * Request parameters
     *
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Browser UA
     */
    private String userAgent;

    // ========== Execute related fields ==========

    /**
     * Start request time
     */
    private LocalDateTime beginTime;
    /**
     * End request time
     */
    private LocalDateTime endTime;
    /**
     * Execution duration，Unit：Milliseconds
     */
    private Integer duration;
    /**
     * Result code
     *
     * Currently used {@link CommonResult#getCode()} Properties
     */
    private Integer resultCode;
    /**
     * Result prompt
     *
     * Currently used {@link CommonResult#getMsg()} Properties
     */
    private String resultMsg;

}
