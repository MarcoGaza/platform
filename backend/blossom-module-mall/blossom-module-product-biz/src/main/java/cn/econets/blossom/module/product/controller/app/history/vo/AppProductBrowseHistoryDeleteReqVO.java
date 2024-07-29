package cn.econets.blossom.module.product.controller.app.history.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "User APP - Delete product browsing history Request VO")
@Data
public class AppProductBrowseHistoryDeleteReqVO {

    @Schema(description = "Products SPU Number array", requiredMode = REQUIRED, example = "29502")
    @NotEmpty(message = "Products SPU The number array cannot be empty")
    private List<Long> spuIds;

}
