package cn.econets.blossom.module.promotion.controller.app.bargain.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - A brief summary of bargaining records Response VO")
@Data
public class AppBargainRecordSummaryRespVO {

    @Schema(description = "Number of bargaining users", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer successUserCount;

    @Schema(description = "Successful bargaining record", requiredMode = Schema.RequiredMode.REQUIRED) // Return only the most recent 7 pcs
    private List<Record> successList;

    @Schema(description = "Successful bargaining record")
    @Data
    public static class Record {

        @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "King**")
        private String nickname;

        @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
        private String avatar;

        @Schema(description = "Activity Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Tiancan Tudou")
        private String activityName;

    }

}
