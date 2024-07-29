package cn.econets.blossom.module.member.api.user.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Information Response DTO
 *
 * 
 */
@Data
public class MemberUserRespDTO {

    /**
     * UserID
     */
    private Long id;
    /**
     * User Nickname
     */
    private String nickname;
    /**
     * Account Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * User avatar
     */
    private String avatar;
    /**
     * Mobile phone
     */
    private String mobile;
    /**
     * Creation time（Registration time）
     */
    private LocalDateTime createTime;

    // ========== Other information ==========

    /**
     * Member Level Number
     */
    private Long levelId;

    /**
     * Points
     */
    private Integer point;

}
