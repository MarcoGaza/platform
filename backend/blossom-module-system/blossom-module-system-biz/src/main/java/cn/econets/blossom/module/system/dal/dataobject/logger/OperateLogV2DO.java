package cn.econets.blossom.module.system.dal.dataobject.logger;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Operation log table V2
 *
 */
@TableName(value = "system_operate_log_v2", autoResultMap = true)
@KeySequence("system_operate_log_seq_v2") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLogV2DO extends BaseDO {

    /**
     * Log primary key
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
     * User Number
     *
     * Relationship MemberUserDO of id Properties，Or AdminUserDO of id Properties
     */
    private Long userId;
    /**
     * User Type
     *
     * Relationship {@link  UserTypeEnum}
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
     * Log content，Record the details of the entire operation
     *
     * For example，Modify the number 1 User information，Change gender from male to female，Change the name from Yudao to Yuanma。
     */
    private String action;
    /**
     * Extended fields，Some complicated business，Some fields need to be recorded ( JSON Format )
     *
     * For example，Record order number，{ orderId: "1"}
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

}
