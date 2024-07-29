package cn.econets.blossom.module.system.controller.admin.user.vo.profile;

import cn.econets.blossom.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.econets.blossom.module.system.controller.admin.dept.vo.post.PostSimpleRespVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RoleSimpleRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Management Backend - User personal center information Response VO")
public class UserProfileRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    private String username;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String nickname;

    @Schema(description = "User mailbox", example = "ryximu@qq.com")
    private String email;

    @Schema(description = "Mobile phone number", example = "15601691300")
    private String mobile;

    @Schema(description = "User gender，See SexEnum Enumeration class", example = "1")
    private Integer sex;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

    @Schema(description = "Last login IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.1.1")
    private String loginIp;

    @Schema(description = "Last login time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime loginDate;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

    /**
     * Character
     */
    private List<RoleSimpleRespVO> roles;
    /**
     * Department
     */
    private DeptSimpleRespVO dept;
    /**
     * Position array
     */
    private List<PostSimpleRespVO> posts;
    /**
     * Social user array
     */
    private List<SocialUser> socialUsers;

    @Schema(description = "Social User")
    @Data
    public static class SocialUser {

        @Schema(description = "Type of social platform，See SocialTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
        private Integer type;

        @Schema(description = "Social user openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "IPRmJ0wvBptiPIlGEZiPewGwiEiE")
        private String openid;

    }

}
