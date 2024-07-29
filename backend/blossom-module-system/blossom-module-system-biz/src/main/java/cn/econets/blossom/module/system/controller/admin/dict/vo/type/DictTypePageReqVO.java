package cn.econets.blossom.module.system.controller.admin.dict.vo.type;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@Schema(description = "Management Backend - Dictionary type pagination list Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypePageReqVO extends PageParam {

    @Schema(description = "Dictionary type name，Fuzzy matching", example = "blossom")
    private String name;

    @Schema(description = "Dictionary type，Fuzzy matching", example = "sys_common_sex")
    @Size(max = 100, message = "The length of dictionary type cannot exceed100Characters")
    private String type;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
