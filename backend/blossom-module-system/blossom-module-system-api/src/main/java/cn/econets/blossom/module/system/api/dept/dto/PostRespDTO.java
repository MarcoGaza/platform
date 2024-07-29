package cn.econets.blossom.module.system.api.dept.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * Position Response DTO
 *
 */
@Data
public class PostRespDTO {

    /**
     * Position number
     */
    private Long id;
    /**
     * Position Name
     */
    private String name;
    /**
     * Position Code
     */
    private String code;
    /**
     * Position sorting
     */
    private Integer sort;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
