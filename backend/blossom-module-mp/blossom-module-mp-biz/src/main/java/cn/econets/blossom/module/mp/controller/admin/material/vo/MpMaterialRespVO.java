package cn.econets.blossom.module.mp.controller.admin.material.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Public account material Response VO")
@Data
public class MpMaterialRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long accountId;
    @Schema(description = "Official account appId", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx1234567890")
    private String appId;

    @Schema(description = "Material media_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "123")
    private String mediaId;

    @Schema(description = "File type See WxConsts.MediaFileType Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "image")
    private String type;

    @Schema(description = "Is it permanent? true - Permanent；false - Temporary", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean permanent;

    @Schema(description = "Material URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String url;


    @Schema(description = "Name", example = "name")
    private String name;

    @Schema(description = "Public account files URL Only【Permanent material】Use", example = "https://mmbiz.qpic.cn/xxx.mp3")
    private String mpUrl;

    @Schema(description = "Title of the video material Only【Permanent material】Use", example = "I am the title")
    private String title;
    @Schema(description = "Description of video material Only【Permanent material】Use", example = "I am introducing")
    private String introduction;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
