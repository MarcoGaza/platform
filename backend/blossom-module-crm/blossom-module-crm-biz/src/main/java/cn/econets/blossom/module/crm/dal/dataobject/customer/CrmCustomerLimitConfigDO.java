package cn.econets.blossom.module.crm.dal.dataobject.customer;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.crm.enums.customer.CrmCustomerLimitConfigTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * Customer restriction configuration DO
 *
 */
@TableName(value = "crm_customer_limit_config", autoResultMap = true)
@KeySequence("crm_customer_limit_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerLimitConfigDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Rule Type
     * <p>
     * Enumeration {@link CrmCustomerLimitConfigTypeEnum}
     */
    private Integer type;
    /**
     * People to whom the rules apply
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> userIds;
    /**
     * Rules applicable departments
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> deptIds;
    /**
     * Quantity limit
     */
    private Integer maxCount;
    /**
     * Whether the transaction customer occupies the number of customers
     *
     * If and only if {@link #type} for 1 Time，Use
     */
    private Boolean dealCountEnabled;

}
