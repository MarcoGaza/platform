package cn.econets.blossom.module.crm.dal.dataobject.permission;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CRM Data permissions DO
 *
 */
@TableName("crm_permission")
@KeySequence("crm_permission_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmPermissionDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;

    /**
     * Data type
     *
     * Enumeration {@link CrmBizTypeEnum}
     */
    private Integer bizType;
    /**
     * Data number
     *
     * Relationship {@link CrmBizTypeEnum} Corresponding module DO of id Field
     */
    private Long bizId;

    /**
     * User Number
     *
     * Relationship AdminUser of id Field
     */
    private Long userId;

    /**
     * Permission level
     *
     * Relationship {@link CrmPermissionLevelEnum}
     */
    private Integer level;

}
