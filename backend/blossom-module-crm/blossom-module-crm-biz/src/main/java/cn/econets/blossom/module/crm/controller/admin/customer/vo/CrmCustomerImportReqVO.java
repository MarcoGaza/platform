package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Customer Import Request VO")
@Data
@Builder
public class CrmCustomerImportReqVO {

    @Schema(description = "Excel File", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Excel The file cannot be empty")
    private MultipartFile file;

    @Schema(description = "Whether to support updates", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to support update cannot be empty")
    private Boolean updateSupport;

    @Schema(description = "Person in charge", example = "1")
    private Long ownerUserId; // for null The customer enters the high seas

}
