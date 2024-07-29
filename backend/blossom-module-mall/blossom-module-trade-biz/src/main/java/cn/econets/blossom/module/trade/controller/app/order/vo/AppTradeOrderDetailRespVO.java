package cn.econets.blossom.module.trade.controller.app.order.vo;

import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemRespVO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderRefundStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "User App - Order transaction details Response VO")
@Data
public class AppTradeOrderDetailRespVO {

    // ========== Basic order information ==========

    @Schema(description = "Order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Order serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1146347329394184195")
    private String no;

    @Schema(description = "Order Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer type;

    @Schema(description = "Order time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

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

    @Schema(description = "Do you want to comment?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean commentStatus;

    // ========== Price + Basic payment information ==========

    @Schema(description = "Has it been paid?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean payStatus;

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long payOrderId;

    @Schema(description = "Payment Time")
    private LocalDateTime payTime;

    @Schema(description = "Payment timeout", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime payExpireTime;

    @Schema(description = "Payment channel", example = "wx_lite_pay")
    private String payChannelCode;
    @Schema(description = "Payment channel name", example = "WeChat Mini Program Payment")
    private String payChannelName;

    @Schema(description = "Original price of product（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalPrice;

    @Schema(description = "Order discount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer discountPrice;

    @Schema(description = "Shipping amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer deliveryPrice;

    @Schema(description = "Order price adjustment（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer adjustPrice;

    @Schema(description = "Amount payable（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer payPrice;

    // ========== Receive + Basic logistics information ==========

    @Schema(description = "Delivery method", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer deliveryType;

    @Schema(description = "Shipping logistics company number", example = "10")
    private Long logisticsId;

    @Schema(description = "Shipping logistics name", example = "SF Express")
    private String logisticsName;

    @Schema(description = "Shipping logistics order number", example = "1024")
    private String logisticsNo;

    @Schema(description = "Shipping time")
    private LocalDateTime deliveryTime;

    @Schema(description = "Delivery time")
    private LocalDateTime receiveTime;

    @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    private String receiverName;

    @Schema(description = "Recipient's mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    private String receiverMobile;

    @Schema(description = "Recipient's area code", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Integer receiverAreaId;

    @Schema(description = "Recipient's region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shanghai Shanghai Putuo District")
    private String receiverAreaName;

    @Schema(description = "Detailed address of recipient", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhongguancun Street 1 Number")
    private String receiverDetailAddress;

    @Schema(description = "Self-pickup store number", example = "1088")
    private Long pickUpStoreId;

    @Schema(description = "Self-collection verification code", example = "40964096")
    private String pickUpVerifyCode;

    // ========== Basic after-sales information ==========

    @Schema(description = "After-sales status", example = "0")
    private Integer refundStatus;

    @Schema(description = "Refund amount，Unit：points", example = "100")
    private Integer refundPrice;

    // ========== Basic marketing information ==========

    @Schema(description = "Coupon number", example = "1024")
    private Long couponId;

    @Schema(description = "Coupon reduction amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer couponPrice;

    @Schema(description = "Amount of points deduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer pointPrice;

    /**
     * Order item array
     */
    private List<AppTradeOrderItemRespVO> items;

}
