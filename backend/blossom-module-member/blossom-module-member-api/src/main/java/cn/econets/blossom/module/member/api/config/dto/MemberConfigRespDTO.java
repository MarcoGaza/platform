package cn.econets.blossom.module.member.api.config.dto;

import lombok.Data;

/**
 * User Information Response DTO
 *
 *
 */
@Data
public class MemberConfigRespDTO {

    /**
     * Points deduction switch
     */
    private Boolean pointTradeDeductEnable;
    /**
     * Points deduction，Unit：Points
     * <p>
     * 1 How many points can be deducted from the points
     */
    private Integer pointTradeDeductUnitPrice;
    /**
     * Maximum Points Deduction
     */
    private Integer pointTradeDeductMaxPrice;
    /**
     * 1 How many points are given for each yuan
     */
    private Integer pointTradeGivePoint;

}
