package cn.econets.blossom.module.trade.controller.admin.order.vo;

import cn.econets.blossom.module.trade.controller.admin.base.member.user.MemberUserRespVO;
import cn.econets.blossom.module.trade.controller.admin.base.product.property.ProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Transaction order details Response VO")
@Data
public class TradeOrderDetailRespVO extends TradeOrderBaseVO {

    /**
     * Line Item List
     */
    private List<Item> items;

    /**
     * Order user information
     */
    private MemberUserRespVO user;
    /**
     * Promote user information
     */
    private MemberUserRespVO brokerageUser;

    /**
     * Operation log list
     */
    private List<OrderLog> logs;

    @Schema(description = "Recipient's region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shanghai Shanghai Putuo District")
    private String receiverAreaName;

    @Schema(description = "Management Backend - Transaction order operation log")
    @Data
    public static class OrderLog {

        @Schema(description = "Operation details", requiredMode = Schema.RequiredMode.REQUIRED, example = "Order delivery")
        private String content;

        @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-06-01 10:50:20")
        private LocalDateTime createTime;

        @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer userType;

    }

    @Schema(description = "Management Backend - Order item of transaction order details")
    @Data
    public static class Item extends TradeOrderItemBaseVO {

        /**
         * Attribute array
         */
        private List<ProductPropertyValueDetailRespVO> properties;

    }

}
