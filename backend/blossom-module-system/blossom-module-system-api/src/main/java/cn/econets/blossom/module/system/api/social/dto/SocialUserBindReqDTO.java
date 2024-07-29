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
 * Unbind social user Request DTO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserBindReqDTO {

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
     * Authorization code
     */
    @NotEmpty(message = "Authorization code cannot be empty")
    private String code;
    /**
     * state
     */
    @NotNull(message = "state Cannot be empty")
    private String state;

}
