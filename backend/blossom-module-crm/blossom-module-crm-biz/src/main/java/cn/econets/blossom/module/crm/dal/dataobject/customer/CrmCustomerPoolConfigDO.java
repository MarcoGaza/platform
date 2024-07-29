package cn.econets.blossom.module.crm.dal.dataobject.customer;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * Customer high seas configuration DO
 *
 */
@TableName(value = "crm_customer_pool_config")
@KeySequence("crm_customer_pool_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerPoolConfigDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Whether to enable client high seas
     */
    private Boolean enabled;
    /**
     * Number of days released into the high seas without follow-up
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer contactExpireDays;
    /**
     * Number of days unsold items were placed on the high seas
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer dealExpireDays;
    /**
     * Whether to enable advance reminder
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Boolean notifyEnabled;
    /**
     * Days of advance reminder
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer notifyDays;

}
