package cn.econets.blossom.module.crm.controller.admin.product.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Product Category Response VO")
@Data
public class CrmProductCategoryRespVO {

    @Schema(description = "Classification Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23902")
    private Long id;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhao Liu")
    private String name;

    @Schema(description = "Parent number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4680")
    private Long parentId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
