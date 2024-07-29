package cn.econets.blossom.module.promotion.controller.app.combination.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Group buying record Response VO")
@Data
public class AppCombinationRecordRespVO {

    @Schema(description = "Group buying record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Group buying activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long activityId;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String avatar;

    @Schema(description = "Expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expireTime;

    @Schema(description = "Number of participants", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer userSize;

    @Schema(description = "Number of people who have joined the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer userCount;

    @Schema(description = "Group buying status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long orderId;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am a big soybean")
    private String spuName;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

    @Schema(description = "Quantity of goods purchased", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer count;

    @Schema(description = "Group purchase amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer combinationPrice;

}
