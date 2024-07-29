package cn.econets.blossom.module.trade.dal.dataobject.order;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderCancelTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderRefundStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Transaction Order DO
 *
 */
@TableName("trade_order")
@KeySequence("trade_order_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrderDO extends BaseDO {

    /**
     * Shipping logistics company number - Empty（No need to ship）
     */
    public static final Long LOGISTICS_ID_NULL = 0L;

    // ========== Basic order information ==========
    /**
     * Order number，Primary key auto-increment
     */
    private Long id;
    /**
     * Order serial number
     *
     * For example，1146347329394184195
     */
    private String no;
    /**
     * Order Type
     *
     * Enumeration {@link TradeOrderTypeEnum}
     */
    private Integer type;
    /**
     * Order source
     *
     * Enumeration {@link TerminalEnum}
     */
    private Integer terminal;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;
    /**
     * User IP
     */
    private String userIp;
    /**
     * User Notes
     */
    private String userRemark;
    /**
     * Order Status
     *
     * Enumeration {@link TradeOrderStatusEnum}
     */
    private Integer status;
    /**
     * Quantity of goods purchased
     */
    private Integer productCount;
    /**
     * Order completion time
     */
    private LocalDateTime finishTime;
    /**
     * Order cancellation time
     */
    private LocalDateTime cancelTime;
    /**
     * Cancel type
     *
     * Enumeration {@link TradeOrderCancelTypeEnum}
     */
    private Integer cancelType;
    /**
     * Merchant Notes
     */
    private String remark;
    /**
     * Do you want to comment?
     *
     * true - Evaluated
     * false - Not rated
     */
    private Boolean commentStatus;

    /**
     * Promoter Number
     *
     * Relationship {@link BrokerageUserDO#getId()} Field，That is {@link MemberUserRespDTO#getId()} Field
     */
    private Long brokerageUserId;

    // ========== Price + Basic payment information ==========

    // Price Document - Taobao：https://open.taobao.com/docV3.htm?docId=108471&docType=1
    // Price Document - Jingdong Home Delivery：https://openo2o.jddj.com/api/getApiDetail/182/4d1494c5e7ac4679bfdaaed950c5bc7f.htm
    // Price Document - You are very good：https://doc.youzanyun.com/detail/API/0/906

    /**
     * Payment order number
     *
     * Connection pay-module-biz Payment order number of payment service，That is PayOrderDO of id Number
     */
    private Long payOrderId;
    /**
     * Has it been paid?
     *
     * true - Already paid
     * false - Never paid
     */
    private Boolean payStatus;
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

    /**
     * Original price of product，Unit：Points
     *
     * totalPrice = {@link TradeOrderItemDO#getPrice()} * {@link TradeOrderItemDO#getCount()} Summation
     *
     * Corresponding taobao of trade.total_fee Field
     */
    private Integer totalPrice;
    /**
     * Discount amount，Unit：Points
     *
     * Corresponding taobao of order.discount_fee Field
     */
    private Integer discountPrice;
    /**
     * Shipping amount，Unit：Points
     */
    private Integer deliveryPrice;
    /**
     * Order price adjustment，Unit：Points
     *
     * Positive number，Price increase；Negative number，Price reduction
     */
    private Integer adjustPrice;
    /**
     * Amount payable（Total），Unit：Points
     *
     * = {@link #totalPrice}
     * - {@link #couponPrice}
     * - {@link #pointPrice}
     * - {@link #discountPrice}
     * + {@link #deliveryPrice}
     * + {@link #adjustPrice}
     * - {@link #vipPrice}
     */
    private Integer payPrice;

    // ========== Receive + Basic logistics information ==========
    /**
     * Delivery method
     *
     * Enumeration {@link DeliveryTypeEnum}
     */
    private Integer deliveryType;
    /**
     * Shipping logistics company number
     *
     * If no shipment is required，Then logisticsId Set to 0。The reason is，I don't want to add extra fields
     *
     * Relationship {@link DeliveryExpressDO#getId()}
     */
    private Long logisticsId;
    /**
     * Shipping logistics order number
     *
     * If no shipment is required，Then logisticsNo Settings ""。The reason is，I don't want to add extra fields
     */
    private String logisticsNo;
    /**
     * Shipping time
     */
    private LocalDateTime deliveryTime;

    /**
     * Delivery time
     */
    private LocalDateTime receiveTime;
    /**
     * Recipient Name
     */
    private String receiverName;
    /**
     * Recipient's mobile phone
     */
    private String receiverMobile;
    /**
     * Recipient's area code
     */
    private Integer receiverAreaId;
    /**
     * Detailed address of recipient
     */
    private String receiverDetailAddress;

    /**
     * Self-pickup store number
     *
     * Relationship {@link DeliveryPickUpStoreDO#getId()}
     */
    private Long pickUpStoreId;
    /**
     * Self-collection verification code
     */
    private String pickUpVerifyCode;

    // ========== Basic after-sales information ==========
    /**
     * After-sales status
     *
     * Enumeration {@link TradeOrderRefundStatusEnum}
     */
    private Integer refundStatus;
    /**
     * Refund amount，Unit：Points
     *
     * Attention，Refund will not affect {@link #payPrice} Actual payment amount
     * That is to say，How much revenue does an order ultimately generate? = payPrice - refundPrice
     */
    private Integer refundPrice;

    // ========== Basic marketing information ==========
    /**
     * Coupon number
     */
    private Long couponId;
    /**
     * Coupon reduction amount，Unit：Points
     *
     * Corresponding taobao of trade.coupon_fee Field
     */
    private Integer couponPrice;
    /**
     * Points used
     */
    private Integer usePoint;
    /**
     * Amount of points deduction，Unit：Points
     *
     * Corresponding taobao of trade.point_fee Field
     */
    private Integer pointPrice;
    /**
     * Gifted points
     */
    private Integer givePoint;
    /**
     * Refunded points used
     */
    private Integer refundPoint;
    /**
     * VIP Reduction amount，Unit：Points
     */
    private Integer vipPrice;

    /**
     * Second sale activity number
     *
     * Relationship SeckillActivityDO of id Field
     */
    private Long seckillActivityId;

    /**
     * Bargaining activity number
     *
     * Relationship BargainActivityDO of id Field
     */
    private Long bargainActivityId;
    /**
     * Bargaining record number
     *
     * Relationship BargainRecordDO of id Field
     */
    private Long bargainRecordId;

    /**
     * Group buying activity number
     *
     * Relationship CombinationActivityDO of id Field
     */
    private Long combinationActivityId;
    /**
     * Group leader number
     *
     * Relationship CombinationRecordDO of headId Field
     */
    private Long combinationHeadId;
    /**
     * Group buying record number
     *
     * Relationship CombinationRecordDO of id Field
     */
    private Long combinationRecordId;

}
