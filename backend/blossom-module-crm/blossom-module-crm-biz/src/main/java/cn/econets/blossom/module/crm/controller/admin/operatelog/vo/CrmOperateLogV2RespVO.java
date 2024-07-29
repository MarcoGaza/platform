package cn.econets.blossom.module.crm.controller.admin.operatelog.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Follow up Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmOperateLogV2RespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private Long id;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String userName;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer userType;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private String type;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "Modify customer")
    private String subType;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private Long bizId;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "Change what from what to what")
    private String action;

    @Schema(description = "Number", example = "{orderId: 1}")
    private String extra;

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-01-01")
    private LocalDateTime createTime;

}
