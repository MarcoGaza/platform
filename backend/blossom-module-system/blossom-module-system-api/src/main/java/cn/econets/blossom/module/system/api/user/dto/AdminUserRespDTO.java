package cn.econets.blossom.module.system.api.user.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

import java.util.Set;

/**
 * Admin User Response DTO
 *
 */
@Data
public class AdminUserRespDTO {

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
     * DepartmentID
     */
    private Long deptId;
    /**
     * Position number array
     */
    private Set<Long> postIds;
    /**
     * Mobile phone number
     */
    private String mobile;

}
