package cn.econets.blossom.module.crm.controller.admin.contact.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - CRM Contact Opportunities Request VO") // For association，Cancel the associated operation
@Data
public class CrmContactBusinessReqVO {

    @Schema(description = "Contact Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20878")
    @NotNull(message="Contact person cannot be empty")
    private Long contactId;

    @Schema(description = "Opportunity number array", requiredMode = Schema.RequiredMode.REQUIRED, example = "7638")
    @NotEmpty(message="Business opportunities cannot be empty")
    private List<Long> businessIds;

}
