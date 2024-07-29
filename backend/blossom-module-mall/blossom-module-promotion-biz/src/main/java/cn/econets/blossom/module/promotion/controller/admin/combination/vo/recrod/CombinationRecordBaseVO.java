package cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Group buying record Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class CombinationRecordBaseVO {

    @Schema(description = "Group buying record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Group buying activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long activityId;

    @Schema(description = "Team Leader Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long headId;

    // ========== User related ==========

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "9430")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "User nickname", example = "Oldeconets")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

    // ========== Product related ==========

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23622")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "29950")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am a big soybean")
    private String spuName;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

    @Schema(description = "Expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime expireTime;

    @Schema(description = "Number of participants", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer userSize;

    @Schema(description = "Number of people who have joined the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer userCount;

    @Schema(description = "Group buying status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Whether to form a virtual group", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean virtualGroup;

    @Schema(description = "Start time (The time when the order starts after payment)", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "End time（Group formation time/Failure time）", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

}
