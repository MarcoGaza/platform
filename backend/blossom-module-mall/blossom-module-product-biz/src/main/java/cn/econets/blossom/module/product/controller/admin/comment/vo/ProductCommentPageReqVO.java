package cn.econets.blossom.module.product.controller.admin.comment.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.product.enums.comment.ProductCommentScoresEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Product Reviews Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductCommentPageReqVO extends PageParam {

    @Schema(description = "Name of the reviewer", example = "Wang Ergou")
    private String userNickname;

    @Schema(description = "Transaction order number", example = "24428")
    private Long orderId;

    @Schema(description = "ProductsSPUNumber", example = "29502")
    private Long spuId;

    @Schema(description = "ProductsSPUName", example = "Cold medicine")
    private String spuName;

    @Schema(description = "Rating Stars 1-5 Points", example = "5")
    @InEnum(ProductCommentScoresEnum.class)
    private Integer scores;

    @Schema(description = "Did the merchant reply?", example = "true")
    private Boolean replyStatus;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
