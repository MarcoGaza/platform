package cn.econets.blossom.module.system.api.dept.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * Department Response DTO
 *
 */
@Data
public class DeptRespDTO {

    /**
     * Department Number
     */
    private Long id;
    /**
     * Department Name
     */
    private String name;
    /**
     * Parent department number
     */
    private Long parentId;
    /**
     * User ID of the person in charge
     */
    private Long leaderUserId;
    /**
     * Department Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
