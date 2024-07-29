package cn.econets.blossom.module.system.controller.admin.tenant.vo.packages;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - Tenant Package Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantPackagePageReqVO extends PageParam {

    @Schema(description = "Package Name", example = "VIP")
    private String name;

    @Schema(description = "Status", example = "1")
    private Integer status;

    @Schema(description = "Remarks", example = "Good")
    private String remark;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;
}
