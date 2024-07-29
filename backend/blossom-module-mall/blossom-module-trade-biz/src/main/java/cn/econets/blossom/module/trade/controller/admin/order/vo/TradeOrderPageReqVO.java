package cn.econets.blossom.module.trade.controller.admin.order.vo;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Transaction order paging Request VO")
@Data
public class TradeOrderPageReqVO extends PageParam {

    @Schema(description = "Order Number", example = "88888888")
    private String no;

    @Schema(description = "User Number", example = "1024")
    private Long userId;

    @Schema(description = "User Nickname", example = "Xiao Wang")
    private String userNickname;

    @Schema(description = "User's mobile phone number", example = "Xiao Wang")
    @Mobile
    private String userMobile;

    @Schema(description = "Delivery method", example = "1")
    private Integer deliveryType;

    @Schema(description = "Shipping logistics company number", example = "1")
    private Long logisticsId;

    @Schema(description = "Self-pickup store number", example = "[1,2]")
    private List<Long> pickUpStoreIds;

    @Schema(description = "Self-collection verification code", example = "12345678")
    private String pickUpVerifyCode;

    @Schema(description = "Order Type", example = "1")
    private Integer type;

    @Schema(description = "Order Status", example = "1")
    @InEnum(value = TradeOrderStatusEnum.class, message = "Order status must be {value}")
    private Integer status;

    @Schema(description = "Payment channel", example = "wx_lite")
    private String payChannelCode;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "Order source", example = "10")
    @InEnum(value = TerminalEnum.class, message = "Order source {value}")
    private Integer terminal;

}
