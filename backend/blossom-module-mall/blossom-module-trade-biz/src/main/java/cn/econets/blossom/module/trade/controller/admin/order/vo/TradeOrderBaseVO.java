package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Transaction Order Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class TradeOrderBaseVO {

    // ========== Basic order information ==========

    @Schema(description = "Order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Order serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1146347329394184195")
    private String no;

    @Schema(description = "Order time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Order Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Order source", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer terminal;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long userId;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String userIp;

    @Schema(description = "User Notes", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    private String userRemark;

    @Schema(description = "Order Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Quantity of goods purchased", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer productCount;

    @Schema(description = "Order completion time")
    private LocalDateTime finishTime;

    @Schema(description = "Order cancellation time")
    private LocalDateTime cancelTime;

    @Schema(description = "Cancel type", example = "10")
    private Integer cancelType;

    @Schema(description = "Merchant Notes", example = "Guess what")
    private String remark;

    // ========== Price + Basic payment information ==========

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long payOrderId;

    @Schema(description = "Has it been paid?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean payStatus;

    @Schema(description = "Payment Time")
    private LocalDateTime payTime;

    @Schema(description = "Payment channel", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_lite")
    private String payChannelCode;

    @Schema(description = "Original price of product（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalPrice;

    @Schema(description = "Order Discount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer discountPrice;

    @Schema(description = "Shipping amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer deliveryPrice;

    @Schema(description = "Order price adjustment（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer adjustPrice;

    @Schema(description = "Amount payable（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer payPrice;

    // ========== Receive + Basic logistics information ==========

    @Schema(description = "Delivery method", example = "10")
    private Integer deliveryType;

    @Schema(description = "Self-pickup store", example = "10")
    private Long pickUpStoreId;

    @Schema(description = "Self-collection verification code", example = "10")
    private Long pickUpVerifyCode;

    @Schema(description = "Delivery template number", example = "1024")
    private Long deliveryTemplateId;

    @Schema(description = "Shipping logistics company number", example = "1024")
    private Long logisticsId;

    @Schema(description = "Shipping logistics order number", example = "1024")
    private String logisticsNo;

    @Schema(description = "Shipping time")
    private LocalDateTime deliveryTime;

    @Schema(description = "Delivery time")
    private LocalDateTime receiveTime;

    @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    private String receiverName;

    @Schema(description = "Recipient's mobile phone", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    private String receiverMobile;

    @Schema(description = "Recipient's area code", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Integer receiverAreaId;

    @Schema(description = "Detailed address of recipient", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhongguancun Street 1 Number")
    private String receiverDetailAddress;

    // ========== Basic after-sales information ==========

    @Schema(description = "After-sales status", example = "1")
    private Integer afterSaleStatus;

    @Schema(description = "Refund amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer refundPrice;

    // ========== Basic marketing information ==========

    @Schema(description = "Coupon number", example = "1024")
    private Long couponId;

    @Schema(description = "Coupon reduction amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer couponPrice;

    @Schema(description = "Amount of points deduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer pointPrice;

    @Schema(description = "VIP Reduction amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "888")
    private Integer vipPrice;

    @Schema(description = "Promoter Number", example = "1")
    private Long brokerageUserId;

}
