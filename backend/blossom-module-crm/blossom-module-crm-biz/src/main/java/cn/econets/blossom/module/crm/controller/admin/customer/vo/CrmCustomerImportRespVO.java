package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "Management Backend - Customer Import Response VO")
@Data
@Builder
public class CrmCustomerImportRespVO {

    @Schema(description = "Created a successful customer name array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createCustomerNames;

    @Schema(description = "Updated successfully customer name array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateCustomerNames;

    @Schema(description = "Import failed customer collection，key Customer name，value Reason for failure", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureCustomerNames;

}
