package cn.econets.blossom.module.crm.service.followup.bo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Follow-up information Update Req BO
 *
 */
@Data
public class CrmUpdateFollowUpReqBO {

    @Schema(description = "Data number", example = "3167")
    @NotNull(message = "Data number cannot be empty")
    private Long bizId;

    @Schema(description = "Last follow-up time")
    @DiffLogField(name = "Last follow-up time")
    private LocalDateTime contactLastTime;

    @Schema(description = "Next contact time")
    @DiffLogField(name = "Next contact time")
    private LocalDateTime contactNextTime;

    @Schema(description = "Latest update content")
    @DiffLogField(name = "Latest update content")
    private String contactLastContent;

}
