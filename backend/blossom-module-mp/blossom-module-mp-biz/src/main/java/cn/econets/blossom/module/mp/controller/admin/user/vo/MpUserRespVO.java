package cn.econets.blossom.module.mp.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "Management Backend - Public Account Fans Response VO")
@Data
public class MpUserRespVO  {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Public account fan logo", requiredMode = Schema.RequiredMode.REQUIRED, example = "o6_bmjrPTlm6_2sgVt7hMZOPfL2M")
    private String openid;

    @Schema(description = "Follow Status See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer subscribeStatus;
    @Schema(description = "Pay attention to time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime subscribeTime;
    @Schema(description = "Unfollow time")
    private LocalDateTime unsubscribeTime;

    @Schema(description = "Nickname", example = "Taro Road")
    private String nickname;
    @Schema(description = "Avatar address", example = "https://www.econets.cn/1.png")
    private String headImageUrl;
    @Schema(description = "Language", example = "zh_CN")
    private String language;
    @Schema(description = "Country", example = "China")
    private String country;
    @Schema(description = "Province", example = "Guangdong Province")
    private String province;
    @Schema(description = "City", example = "Guangzhou")
    private String city;
    @Schema(description = "Remarks", example = "You are a taro")
    private String remark;

    @Schema(description = "Tag number array", example = "1,2,3")
    private List<Long> tagIds;

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long accountId;
    @Schema(description = "Official account appId", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx1234567890")
    private String appId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
