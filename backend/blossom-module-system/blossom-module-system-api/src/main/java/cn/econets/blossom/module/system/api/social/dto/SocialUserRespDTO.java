package cn.econets.blossom.module.system.api.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Social user Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserRespDTO {

    /**
     * Social user openid
     */
    private String openid;
    /**
     * Nickname of social user
     */
    private String nickname;
    /**
     * Social user's avatar
     */
    private String avatar;

    /**
     * Associated user number
     */
    private Long userId;

}
