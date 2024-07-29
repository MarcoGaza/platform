package cn.econets.blossom.module.crm.controller.admin.followup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Add follow-up records/Modify Request VO")
@Data
public class CrmFollowUpRecordSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "28800")
    private Long id;

    @Schema(description = "Data type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Data type cannot be empty")
    private Integer bizType;

    @Schema(description = "Data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "5564")
    @NotNull(message = "Data number cannot be empty")
    private Long bizId;

    @Schema(description = "Follow-up type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Follow-up type cannot be empty")
    private Integer type;

    @Schema(description = "Follow-up content", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Follow-up content cannot be empty")
    private String content;

    @Schema(description = "Next contact time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The next contact time cannot be empty")
    private LocalDateTime nextTime;

    @Schema(description = "Array of associated opportunity numbers")
    private List<Long> businessIds;
    @Schema(description = "Array of associated contact numbers")
    private List<Long> contactIds;

    @Schema(description = "Picture")
    private List<String> picUrls;
    @Schema(description = "Attachment")
    private List<String> fileUrls;

}
