package cn.econets.blossom.module.member.api.level.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * Member Level Resp DTO
 *
 * 
 */
@Data
public class MemberLevelRespDTO {

    /**
     * Number
     */
    private Long id;
    /**
     * Level Name
     */
    private String name;
    /**
     * Level
     */
    private Integer level;
    /**
     * Upgrade Experience
     */
    private Integer experience;
    /**
     * Enjoy discount
     */
    private Integer discountPercent;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
