package cn.econets.blossom.module.pay.dal.dataobject.demo;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Sample Order
 *
 * Demonstration of business system order，How to access pay System payment and refund
 *
 *
 */
@TableName("pay_demo_order")
@KeySequence("pay_demo_order_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayDemoOrderDO extends BaseDO {

    /**
     * Order Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * User Number
     */
    private Long userId;
    /**
     * Product Number
     */
    private Long spuId;
    /**
     * Product Name
     */
    private String spuName;
    /**
     * Price，Unit：Points
     */
    private Integer price;

    // ========== Payment related fields ==========

    /**
     * Do you want to pay?
     */
    private Boolean payStatus;
    /**
     * Payment order number
     *
     * Connection pay-module-biz Payment order number of payment service，That is PayOrderDO of id Number
     */
    private Long payOrderId;
    /**
     * Payment Time
     */
    private LocalDateTime payTime;
    /**
     * Payment channel
     *
     * Corresponding PayChannelEnum Enumeration
     */
    private String payChannelCode;

    // ========== Refund related fields ==========
    /**
     * Payment refund order number
     */
    private Long payRefundId;
    /**
     * Refund amount，Unit：Points
     */
    private Integer refundPrice;
    /**
     * Refund completion time
     */
    private LocalDateTime refundTime;

}
