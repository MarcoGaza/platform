package cn.econets.blossom.module.trade.controller.app.order.vo;

import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "User App - Trading order settlement Request VO")
@Data
@Valid
public class AppTradeOrderSettlementReqVO {

    @Schema(description = "Product item array", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Product cannot be empty")
    private List<Item> items;

    @Schema(description = "Coupon number", example = "1024")
    private Long couponId;

    @Schema(description = "Whether to use points", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to use points cannot be empty")
    private Boolean pointStatus;

    // ========== Delivery related fields ==========
    @Schema(description = "Delivery method", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = DeliveryTypeEnum.class, message = "Incorrect delivery method")
    private Integer deliveryType;

    @Schema(description = "Receiving address number", example = "1")
    private Long addressId;

    @Schema(description = "Self-pickup store number", example = "1088")
    private Long pickUpStoreId;
    @Schema(description = "Recipient Name", example = "econets") // When choosing store pickup，This field is the contact name
    private String receiverName;
    @Schema(description = "Recipient's mobile phone number", example = "15601691300") // When choosing store pickup，This field is the contact's mobile phone number
    @Mobile(message = "The recipient's phone number format is incorrect")
    private String receiverMobile;

    // ========== Second sale related fields ==========
    @Schema(description = "Second sale activity number", example = "1024")
    private Long seckillActivityId;

    // ========== Group buying activity related fields ==========
    @Schema(description = "Group buying activity number", example = "1024")
    private Long combinationActivityId;

    @Schema(description = "Group leader number", example = "2048")
    private Long combinationHeadId;

    // ========== Bargaining activity related fields ==========
    @Schema(description = "Bargaining record number", example = "123")
    private Long bargainRecordId;

    @AssertTrue(message = "Only one specification of the promotional product can be purchased at a time")
    @JsonIgnore
    public boolean isValidActivityItems() {
        // Check whether it is an active order
        if (ObjUtil.isAllEmpty(seckillActivityId, combinationActivityId, combinationHeadId, bargainRecordId)) {
            return true;
        }
        // Check if the order item exceeds the limit
        return items.size() == 1;
    }

    @Data
    @Schema(description = "User App - Product Item")
    @Valid
    public static class Item {

        @Schema(description = "Products SKU Number", example = "2048")
        @NotNull(message = "Products SKU The number cannot be empty")
        private Long skuId;

        @Schema(description = "Purchase quantity", example = "1")
        @Min(value = 1, message = "The minimum purchase quantity is {value}")
        private Integer count;

        @Schema(description = "The shopping cart item ID", example = "1024")
        private Long cartId;

        @AssertTrue(message = "Incorrect product")
        @JsonIgnore
        public boolean isValid() {
            // Combination 1：skuId + count Use goods SKU
            if (skuId != null && count != null) {
                return true;
            }
            // Combination 2：cartId Use shopping cart item
            return cartId != null;
        }

    }

}
