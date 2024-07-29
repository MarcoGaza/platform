package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Example category list Request VO")
@Data
public class Demo02CategoryListReqVO {

    @Schema(description = "Name", example = "econets")
    private String name;

    @Schema(description = "Parent number", example = "6080")
    private Long parentId;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
