package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Commission Record Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrokerageRecordRespVO extends BrokerageRecordBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "28896")
    private Integer id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;


    // ========== User Information ==========

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String userAvatar;
    @Schema(description = "User Nickname", example = "Li Si")
    private String userNickname;


    // ========== Source user information ==========

    @Schema(description = "Source user avatar", example = "https://www.econets.cn/xxx.png")
    private String sourceUserAvatar;
    @Schema(description = "Source user nickname", example = "Li Si")
    private String sourceUserNickname;
}
