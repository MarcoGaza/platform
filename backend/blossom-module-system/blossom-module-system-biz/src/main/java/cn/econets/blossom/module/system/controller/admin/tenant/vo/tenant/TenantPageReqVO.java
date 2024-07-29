package cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - Tenant paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantPageReqVO extends PageParam {

    @Schema(description = "Tenant Name", example = "blossom")
    private String name;

    @Schema(description = "Contact Person", example = "econets")
    private String contactName;

    @Schema(description = "Contact phone number", example = "15601691300")
    private String contactMobile;

    @Schema(description = "Tenant Status（0Normal 1Disable）", example = "1")
    private Integer status;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
