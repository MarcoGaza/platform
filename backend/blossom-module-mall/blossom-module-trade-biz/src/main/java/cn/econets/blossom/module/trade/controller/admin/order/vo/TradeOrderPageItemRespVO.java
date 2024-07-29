package cn.econets.blossom.module.trade.controller.admin.order.vo;

import cn.econets.blossom.module.trade.controller.admin.base.member.user.MemberUserRespVO;
import cn.econets.blossom.module.trade.controller.admin.base.product.property.ProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Management Backend - Trading order pagination items Response VO")
@Data
public class TradeOrderPageItemRespVO extends TradeOrderBaseVO {

    @Schema(description = "Recipient's region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shanghai Shanghai Putuo District")
    private String receiverAreaName;

    @Schema(description = "Line Item List", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Item> items;

    @Schema(description = "User Information", requiredMode = Schema.RequiredMode.REQUIRED)
    private MemberUserRespVO user;

    @Schema(description = "Promoter information")
    private MemberUserRespVO brokerageUser;

    @Schema(description = "Management Backend - The order item of the paginated item of the transaction order")
    @Data
    public static class Item extends TradeOrderItemBaseVO {

        @Schema(description = "Property List", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<ProductPropertyValueDetailRespVO> properties;

    }

}
