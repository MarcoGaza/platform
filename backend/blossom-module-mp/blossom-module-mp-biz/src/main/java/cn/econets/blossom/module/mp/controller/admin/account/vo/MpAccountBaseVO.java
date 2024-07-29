package cn.econets.blossom.module.mp.controller.admin.account.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Public Account Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 *
 */
@Data
public class MpAccountBaseVO {

    @Schema(description = "Public Account Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    @NotEmpty(message = "The public account name cannot be empty")
    private String name;

    @Schema(description = "WeChat public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossomyuanma")
    @NotEmpty(message = "The WeChat ID of the public account cannot be empty")
    private String account;

    @Schema(description = "Public Account appId", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx5b23ba7a5589ecbb")
    @NotEmpty(message = "Public Account appId Cannot be empty")
    private String appId;

    @Schema(description = "Public account key", requiredMode = Schema.RequiredMode.REQUIRED, example = "3a7b3b20c537e52e74afd395eb85f61f")
    @NotEmpty(message = "The public account key cannot be empty")
    private String appSecret;

    @Schema(description = "Public Account token", requiredMode = Schema.RequiredMode.REQUIRED, example = "kangdayuzhen")
    @NotEmpty(message = "Public Account token Cannot be empty")
    private String token;

    @Schema(description = "Encryption Key", example = "gjN+Ksei")
    private String aesKey;

    @Schema(description = "Remarks", example = "Please pay attention to the source code of Yudao，Learning Technology")
    private String remark;

}
