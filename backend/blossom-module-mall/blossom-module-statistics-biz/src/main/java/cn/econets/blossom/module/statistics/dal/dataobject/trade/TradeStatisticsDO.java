package cn.econets.blossom.module.statistics.dal.dataobject.trade;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Transaction Statistics DO
 * <p>
 * Using the sky as the dimension，Statistics of all data
 *
 */
@TableName("trade_statistics")
@KeySequence("trade_statistics_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeStatisticsDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;

    /**
     * Statistical date
     */
    private LocalDateTime time;

    /**
     * Number of orders created
     */
    private Integer orderCreateCount;
    /**
     * Number of items in paid order
     */
    private Integer orderPayCount;
    /**
     * Total payment amount，Unit：Points
     */
    private Integer orderPayPrice;

    /**
     * Number of refunded orders
     */
    private Integer afterSaleCount;
    /**
     * Total refund amount，Unit：Points
     */
    private Integer afterSaleRefundPrice;

    /**
     * Commission amount（Settled），Unit：Points
     */
    private Integer brokerageSettlementPrice;

    /**
     * Total payment amount（Balance），Unit：Points
     */
    private Integer walletPayPrice;
    /**
     * Number of recharge orders
     * <p>
     * From PayWalletRechargeDO Calculation
     */
    private Integer rechargePayCount;
    /**
     * Recharge amount，Unit：Points
     */
    private Integer rechargePayPrice;
    /**
     * Number of recharge refund orders
     */
    private Integer rechargeRefundCount;
    /**
     * Recharge refund amount，Unit：Points
     */
    private Integer rechargeRefundPrice;

}
