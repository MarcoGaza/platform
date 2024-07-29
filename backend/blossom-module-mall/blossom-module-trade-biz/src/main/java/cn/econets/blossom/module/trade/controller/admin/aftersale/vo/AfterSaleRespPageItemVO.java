package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import cn.econets.blossom.module.trade.controller.admin.base.member.user.MemberUserRespVO;
import cn.econets.blossom.module.trade.controller.admin.base.product.property.ProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Every record of the transaction after-sales paging Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AfterSaleRespPageItemVO extends AfterSaleBaseVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "27630")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    /**
     * Product attribute array
     */
    private List<ProductPropertyValueDetailRespVO> properties;

    /**
     * User Information
     */
    private MemberUserRespVO user;

}
