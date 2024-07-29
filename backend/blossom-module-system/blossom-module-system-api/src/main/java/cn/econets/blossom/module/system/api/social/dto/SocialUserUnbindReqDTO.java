package cn.econets.blossom.module.system.api.social.dto;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Social Binding Request DTO，Use code Authorization code
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUserUnbindReqDTO {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     */
    @InEnum(UserTypeEnum.class)
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    /**
     * Type of social platform
     */
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "The type of social platform cannot be empty")
    private Integer socialType;

    /**
     * Social platform openid
     */
    @NotEmpty(message = "Social platform openid Cannot be empty")
    private String openid;

}
