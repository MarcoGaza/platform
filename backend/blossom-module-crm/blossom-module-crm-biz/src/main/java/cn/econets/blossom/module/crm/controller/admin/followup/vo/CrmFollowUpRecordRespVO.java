package cn.econets.blossom.module.crm.controller.admin.followup.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_FOLLOW_UP_TYPE;

@Schema(description = "Management Backend - Follow-up records Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmFollowUpRecordRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "28800")
    private Long id;

    @Schema(description = "Data type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer bizType;

    @Schema(description = "Data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "5564")
    private Long bizId;

    @Schema(description = "Follow-up type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @DictFormat(CRM_FOLLOW_UP_TYPE)
    private Integer type;

    @Schema(description = "Follow-up content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Next contact time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextTime;

    @Schema(description = "Array of associated opportunity numbers")
    private List<Long> businessIds;
    @Schema(description = "Array of associated opportunity names")
    private List<String> businessNames;

    @Schema(description = "Array of associated contact numbers")
    private List<Long> contactIds;
    @Schema(description = "Array of associated contact names")
    private List<String> contactNames;

    @Schema(description = "Picture")
    private List<String> picUrls;
    @Schema(description = "Attachment")
    private List<String> fileUrls;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
