package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import cn.econets.blossom.framework.common.validation.Mobile;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Set;

@Schema(description = "Management Backend - User creation/Modify Request VO")
@Data
public class UserSaveReqVO {

    @Schema(description = "User Number", example = "1024")
    private Long id;

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @NotBlank(message = "User account cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "User account by Number、Letters Composition")
    @Size(min = 4, max = 30, message = "User account length is 4-30 Characters")
    private String username;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @Size(max = 30, message = "User nickname length cannot exceed30Characters")
    private String nickname;

    @Schema(description = "Remarks", example = "I am a user")
    private String remark;

    @Schema(description = "DepartmentID", example = "I am a user")
    private Long deptId;

    @Schema(description = "Position number array", example = "1")
    private Set<Long> postIds;

    @Schema(description = "User mailbox", example = "ryximu@qq.com")
    @Email(message = "The email format is incorrect")
    @Size(max = 50, message = "The length of the email address cannot exceed 50 Characters")
    private String email;

    @Schema(description = "Mobile phone number", example = "15601691300")
    @Mobile
    private String mobile;

    @Schema(description = "User gender，See SexEnum Enumeration class", example = "1")
    private Integer sex;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

    // ========== Only【Create】Time，Fields that need to be passed ==========

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String password;

    @AssertTrue(message = "The password cannot be empty")
    @JsonIgnore
    public boolean isPasswordValid() {
        return id != null // When modifying，No need to pass
                || (ObjectUtil.isAllNotEmpty(password)); // When adding，Must pass all password
    }

}
