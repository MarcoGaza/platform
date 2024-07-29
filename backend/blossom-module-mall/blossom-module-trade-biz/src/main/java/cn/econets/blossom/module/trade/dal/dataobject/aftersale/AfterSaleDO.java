package cn.econets.blossom.module.trade.dal.dataobject.aftersale;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleTypeEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleWayEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * After-sales order，For processing {@link TradeOrderDO} Refund and return process for transaction orders
 *
 */
@TableName(value = "trade_after_sale", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AfterSaleDO extends BaseDO {

    /**
     * After-sales number，Primary key auto-increment
     */
    private Long id;
    /**
     * After-sales order number
     *
     * For example，1146347329394184195
     */
    private String no;
    /**
     * Refund Status
     *
     * Enumeration {@link AfterSaleStatusEnum}
     */
    private Integer status;
    /**
     * After-sales service
     *
     * Enumeration {@link AfterSaleWayEnum}
     */
    private Integer way;
    /**
     * After-sales type
     *
     * Enumeration {@link AfterSaleTypeEnum}
     */
    private Integer type;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;
    /**
     * Reason for application
     *
     * type = Refund，Corresponding trade_after_sale_refund_reason Type
     * type = Return and Refund，Corresponding trade_after_sale_refund_and_return_reason Type
     */
    private String applyReason;
    /**
     * Additional description
     */
    private String applyDescription;
    /**
     * Supplementary voucher image
     *
     * Array，Separate by commas
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> applyPicUrls;

    // ========== Transaction order related ==========
    /**
     * Transaction order number
     *
     * Relationship {@link TradeOrderDO#getId()}
     */
    private Long orderId;
    /**
     * Order serial number
     *
     * Redundant {@link TradeOrderDO#getNo()}
     */
    private String orderNo;
    /**
     * Transaction order item number
     *
     * Relationship {@link TradeOrderItemDO#getId()}
     */
    private Long orderItemId;
    /**
     * Products SPU Number
     *
     * Relationship ProductSpuDO of id Field
     * Redundant {@link TradeOrderItemDO#getSpuId()}
     */
    private Long spuId;
    /**
     * Products SPU Name
     *
     * Relationship ProductSkuDO of name Field
     * Redundant {@link TradeOrderItemDO#getSpuName()}
     */
    private String spuName;
    /**
     * Products SKU Number
     *
     * Relationship ProductSkuDO Number
     */
    private Long skuId;
    /**
     * Attribute array，JSON Format
     *
     * Redundant {@link TradeOrderItemDO#getProperties()}
     */
    @TableField(typeHandler = TradeOrderItemDO.PropertyTypeHandler.class)
    private List<TradeOrderItemDO.Property> properties;
    /**
     * Product image
     *
     * Redundant {@link TradeOrderItemDO#getPicUrl()}
     */
    private String picUrl;
    /**
     * Number of returned goods
     */
    private Integer count;

    // ========== Approval related ==========

    /**
     * Approval time
     */
    private LocalDateTime auditTime;
    /**
     * Approver
     *
     * Relationship AdminUserDO of id Number
     */
    private Long auditUserId;
    /**
     * Approval Notes
     *
     * Attention，It will only be filled in if the approval fails
     */
    private String auditReason;

    // ========== Refund related ==========re
    /**
     * Refund amount，Unit：Points。
     */
    private Integer refundPrice;
    /**
     * Payment refund number
     *
     * Connection pay-module-biz Payment service refund order number，That is PayRefundDO of id Number
     */
    private Long payRefundId;
    /**
     * Refund Time
     */
    private LocalDateTime refundTime;

    // ========== Return related ==========
    /**
     * Return logistics company number
     *
     * Relationship LogisticsDO of id Number
     */
    private Long logisticsId;
    /**
     * Return logistics order number
     */
    private String logisticsNo;
    /**
     * Return Time
     */
    private LocalDateTime deliveryTime;
    /**
     * Delivery time
     */
    private LocalDateTime receiveTime;
    /**
     * Receipt Notes
     *
     * Attention，This will only be filled in if you refuse to receive the goods
     */
    private String receiveReason;

}
