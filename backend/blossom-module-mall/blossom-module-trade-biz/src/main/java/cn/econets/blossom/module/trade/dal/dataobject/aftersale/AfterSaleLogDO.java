package cn.econets.blossom.module.trade.dal.dataobject.aftersale;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleOperateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Transaction and after-sales log DO
 *
 */
@TableName("trade_after_sale_log")
@KeySequence("trade_after_sale_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleLogDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship 1：AdminUserDO of id Field
     * Relationship 2：MemberUserDO of id Field
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * After-sales number
     *
     * Relationship {@link AfterSaleDO#getId()}
     */
    private Long afterSaleId;
    /**
     * Status before operation
     */
    private Integer beforeStatus;
    /**
     * Status after operation
     */
    private Integer afterStatus;

    /**
     * Operation type
     *
     * Enumeration {@link AfterSaleOperateTypeEnum}
     */
    private Integer operateType;
    /**
     * Operation details
     */
    private String content;

}
