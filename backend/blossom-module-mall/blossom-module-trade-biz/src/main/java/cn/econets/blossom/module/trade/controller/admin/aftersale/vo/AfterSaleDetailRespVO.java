package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.log.AfterSaleLogRespVO;
import cn.econets.blossom.module.trade.controller.admin.base.member.user.MemberUserRespVO;
import cn.econets.blossom.module.trade.controller.admin.base.product.property.ProductPropertyValueDetailRespVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderBaseVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderItemBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Management Backend - After-sales order details Response VO")
@Data
public class AfterSaleDetailRespVO extends AfterSaleBaseVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;



    /**
     * Basic order information
     */
    private TradeOrderBaseVO order;
    /**
     * List of order items
     */
    private OrderItem orderItem;

    /**
     * User Information
     */
    private MemberUserRespVO user;

    /**
     * After-sales log
     */
    private List<AfterSaleLogRespVO> logs;

    @Schema(description = "Management Backend - Order item of transaction order details")
    @Data
    public static class OrderItem extends TradeOrderItemBaseVO {

        /**
         * Attribute array
         */
        private List<ProductPropertyValueDetailRespVO> properties;

    }

}
