package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Table definition paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTablePageReqVO extends PageParam {

    @Schema(description = "Table name，Fuzzy matching", example = "blossom")
    private String tableName;

    @Schema(description = "Table description，Fuzzy matching", example = "Table description")
    private String tableComment;

    @Schema(description = "Entity，Fuzzy matching", example = "blossom")
    private String className;

    @Schema(description = "Creation time", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
