package cn.econets.blossom.module.product.controller.admin.history.vo;

import cn.econets.blossom.framework.common.pojo.SortablePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Product browsing history paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductBrowseHistoryPageReqVO extends SortablePageParam {

    @Schema(description = "User Number", example = "4314")
    private Long userId;

    @Schema(description = "Whether to delete the user", example = "false")
    private Boolean userDeleted;

    @Schema(description = "Products SPU Number", example = "42")
    private Long spuId;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}