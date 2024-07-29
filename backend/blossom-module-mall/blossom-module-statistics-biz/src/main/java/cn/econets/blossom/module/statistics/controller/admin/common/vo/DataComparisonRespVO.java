package cn.econets.blossom.module.statistics.controller.admin.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - Data comparison Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataComparisonRespVO<T> {

    @Schema(description = "Current data", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private T value;

    @Schema(description = "Reference data", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private T reference;

}
