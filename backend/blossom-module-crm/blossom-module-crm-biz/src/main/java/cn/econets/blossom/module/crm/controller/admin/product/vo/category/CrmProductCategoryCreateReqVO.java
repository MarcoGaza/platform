package cn.econets.blossom.module.crm.controller.admin.product.vo.category;

import com.mzt.logapi.starter.annotation.DiffLogField;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Product Category Creation/Update Request VO")
@Data
public class CrmProductCategoryCreateReqVO{

    @Schema(description = "Classification number", example = "23902")
    private Long id;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhao Liu")
    @NotNull(message = "Category name cannot be empty")
    @DiffLogField(name = "Category Name")
    private String name;

    @Schema(description = "Parent number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4680")
    @NotNull(message = "The parent number cannot be empty")
    private Long parentId;

}
