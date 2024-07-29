package cn.econets.blossom.module.trade.dal.dataobject.order;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderOperateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Order log DO
 *
 */
@TableName("trade_order_log")
@KeySequence("trade_order_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrderLogDO extends BaseDO {

    /**
     * User Type - System
     *
     * For example：Job Automatically expire orders，Automatically operated by the system
     */
    public static final Integer USER_TYPE_SYSTEM = 0;
    /**
     * User Number - System
     */
    public static final Long USER_ID_SYSTEM = 0L;

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship AdminUserDO of id Field、Or MemberUserDO of id Field
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * Order Number
     *
     * Relationship {@link TradeOrderDO#getId()}
     */
    private Long orderId;
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
     * {@link TradeOrderOperateTypeEnum}
     */
    private Integer operateType;
    /**
     * Order log information
     */
    private String content;

}
