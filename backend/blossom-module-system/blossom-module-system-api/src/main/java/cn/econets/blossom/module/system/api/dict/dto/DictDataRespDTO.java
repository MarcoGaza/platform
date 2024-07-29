package cn.econets.blossom.module.system.api.dict.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * Dictionary data Response DTO
 *
 */
@Data
public class DictDataRespDTO {

    /**
     * Dictionary tags
     */
    private String label;
    /**
     * Dictionary value
     */
    private String value;
    /**
     * Dictionary type
     */
    private String dictType;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
