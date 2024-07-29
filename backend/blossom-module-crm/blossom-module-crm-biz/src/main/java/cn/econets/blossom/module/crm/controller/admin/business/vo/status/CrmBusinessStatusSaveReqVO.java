package cn.econets.blossom.module.crm.controller.admin.business.vo.status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - New business opportunity status/Modify Request VO")
@Data
public class CrmBusinessStatusSaveReqVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "23899")
    private Long id;

    @Schema(description = "Status type number", requiredMode = Schema.RequiredMode.REQUIRED, example = "7139")
    @NotNull(message = "The status type number cannot be empty")
    private Long typeId;

    @Schema(description = "Status name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @NotEmpty(message = "The status name cannot be empty")
    private String name;

    // TODO @lzxhqs：：percent Should be Integer；
    @Schema(description = "Winning rate")
    private String percent;

    // TODO @lzxhqs：Does this not need to be passed when adding or modifying the front end?，Calculate it in order，Just store it；
    @Schema(description = "Sort")
    private Integer sort;

}
