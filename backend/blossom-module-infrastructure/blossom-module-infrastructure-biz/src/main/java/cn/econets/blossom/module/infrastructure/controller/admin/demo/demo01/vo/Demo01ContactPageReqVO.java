package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Example Contacts Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Demo01ContactPageReqVO extends PageParam {

    @Schema(description = "Name", example = "Zhang San")
    private String name;

    @Schema(description = "Gender", example = "1")
    private Integer sex;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
