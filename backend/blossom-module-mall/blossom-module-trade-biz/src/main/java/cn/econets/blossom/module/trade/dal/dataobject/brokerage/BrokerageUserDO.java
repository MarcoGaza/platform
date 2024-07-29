package cn.econets.blossom.module.trade.dal.dataobject.brokerage;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Distribution User DO
 *
 */
@TableName("trade_brokerage_user")
@KeySequence("trade_brokerage_user_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageUserDO extends BaseDO {

    /**
     * User Number
     * <p>
     * Corresponding MemberUserDO of id Field
     */
    @TableId
    private Long id;

    /**
     * Promoter Number
     * <p>
     * Relationship MemberUserDO of id Field
     */
    private Long bindUserId;
    /**
     * Promoter binding time
     */
    private LocalDateTime bindUserTime;

    /**
     * Is it eligible for distribution?
     */
    private Boolean brokerageEnabled;
    /**
     * Time to become a distributor
     */
    private LocalDateTime brokerageTime;

    /**
     * Available commission
     */
    private Integer brokeragePrice;
    /**
     * Freeze commission
     */
    private Integer frozenPrice;
}
