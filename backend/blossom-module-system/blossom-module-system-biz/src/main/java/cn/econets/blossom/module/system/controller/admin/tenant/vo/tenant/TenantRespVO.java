package cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Tenant Response VO")
@Data
@ExcelIgnoreUnannotated
public class TenantRespVO {

    @Schema(description = "Tenant Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Tenant Number")
    private Long id;

    @Schema(description = "Tenant Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @ExcelProperty("Tenant Name")
    private String name;

    @Schema(description = "Contact Person", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @ExcelProperty("Contact Person")
    private String contactName;

    @Schema(description = "Contact phone number", example = "15601691300")
    @ExcelProperty("Contact phone number")
    private String contactMobile;

    @Schema(description = "Tenant Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Bind domain name", example = "https://www.econets.cn")
    private String website;

    @Schema(description = "Tenant package number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long packageId;

    @Schema(description = "Expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expireTime;

    @Schema(description = "Number of accounts", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer accountCount;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
