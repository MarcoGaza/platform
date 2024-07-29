package cn.econets.blossom.module.system.controller.admin.oauth2.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - OAuth2 Update basic user information Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserUpdateReqVO {

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @Size(max = 30, message = "User nickname length cannot exceed 30 Characters")
    private String nickname;

    @Schema(description = "User mailbox", example = "ryximu@qq.com")
    @Email(message = "The email format is incorrect")
    @Size(max = 50, message = "The length of the email address cannot exceed 50 Characters")
    private String email;

    @Schema(description = "Mobile phone number", example = "15601691300")
    @Length(min = 11, max = 11, message = "Mobile phone number length must be 11 position")
    private String mobile;

    @Schema(description = "User gender，See SexEnum Enumeration class", example = "1")
    private Integer sex;

}
