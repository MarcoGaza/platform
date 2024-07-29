package cn.econets.blossom.module.promotion.api.combination.dto;

import lombok.Data;

/**
 * Creation of group purchase record Response DTO
 *
 */
@Data
public class CombinationRecordCreateRespDTO {

    /**
     * Group buying activity number
     *
     * Relationship CombinationActivityDO of id Field
     */
    private Long combinationActivityId;
    /**
     * Group leader number
     *
     * Relationship CombinationRecordDO of headId Field
     */
    private Long combinationHeadId;
    /**
     * Group purchase record number
     *
     * Relationship CombinationRecordDO of id Field
     */
    private Long combinationRecordId;

}
