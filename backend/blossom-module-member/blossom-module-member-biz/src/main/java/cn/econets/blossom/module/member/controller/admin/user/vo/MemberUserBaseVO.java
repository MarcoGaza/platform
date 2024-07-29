package cn.econets.blossom.module.member.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * Member User Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberUserBaseVO {

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    @NotNull(message = "Mobile number cannot be empty")
    private String mobile;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Status cannot be empty")
    private Byte status;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "User nickname cannot be empty")
    private String nickname;

    @Schema(description = "Avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/x.png")
    @URL(message = "The avatar must be URL Format")
    private String avatar;

    @Schema(description = "User Nickname", example = "Li Si")
    private String name;

    @Schema(description = "User gender", example = "1")
    private Byte sex;

    @Schema(description = "Location number", example = "4371")
    private Long areaId;

    @Schema(description = "Full location", example = "Putuo District, Shanghai")
    private String areaName;

    @Schema(description = "Date of Birth", example = "2023-03-12")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime birthday;

    @Schema(description = "Member Notes", example = "I am a small note")
    private String mark;

    @Schema(description = "Member Tag", example = "[1, 2]")
    private List<Long> tagIds;

    @Schema(description = "Member Level Number", example = "1")
    private Long levelId;

    @Schema(description = "User group number", example = "1")
    private Long groupId;

}
