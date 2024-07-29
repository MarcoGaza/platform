package cn.econets.blossom.module.trade.controller.app.aftersale.vo;

import cn.econets.blossom.module.trade.controller.app.base.property.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Transaction and after-sales service Response VO")
@Data
public class AppAfterSaleRespVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "After-sales serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1146347329394184195")
    private String no;

    @Schema(description = "After-sales status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "After-sales service", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer way;

    @Schema(description = "After-sales type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Reason for application", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String applyReason;

    @Schema(description = "Additional description", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String applyDescription;

    @Schema(description = "Supplementary voucher image", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private List<String> applyPicUrls;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    // ========== Transaction order related ==========

    @Schema(description = "Transaction order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long orderId;

    @Schema(description = "Transaction order serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderNo;

    @Schema(description = "Transaction order item number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long orderItemId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long spuId;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String spuName;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long skuId;

    /**
     * Attribute array
     */
    private List<AppProductPropertyValueDetailRespVO> properties;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/01.jpg")
    private String picUrl;

    @Schema(description = "Number of returned goods", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer count;

    // ========== Approval related ==========

    /**
     * Approval Notes
     *
     * Attention，It will only be filled in if the approval fails
     */
    private String auditReason;

    // ========== Refund related ==========

    @Schema(description = "Refund amount，Unit：Points", example = "100")
    private Integer refundPrice;

    @Schema(description = "Refund Time")
    private LocalDateTime refundTime;

    // ========== Return related ==========

    @Schema(description = "Return logistics company number", example = "1")
    private Long logisticsId;

    @Schema(description = "Return logistics order number", example = "SF123456789")
    private String logisticsNo;

    @Schema(description = "Return Time")
    private LocalDateTime deliveryTime;

    @Schema(description = "Delivery time")
    private LocalDateTime receiveTime;

    @Schema(description = "Receipt Notes")
    private String receiveReason;

}
