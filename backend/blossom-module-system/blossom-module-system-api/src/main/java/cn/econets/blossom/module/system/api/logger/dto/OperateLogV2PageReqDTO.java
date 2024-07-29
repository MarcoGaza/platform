package cn.econets.blossom.module.system.api.logger.dto;

import cn.econets.blossom.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * Operation log paging Request DTO
 *
 */
@Data
public class OperateLogV2PageReqDTO extends PageParam {

    /**
     * Module type
     */
    private String bizType;
    /**
     * Module data number
     */
    private Long bizId;

    /**
     * User Number
     */
    private Long userId;

}
