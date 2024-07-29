package cn.econets.blossom.module.member.api.address.dto;

import lombok.Data;

/**
 * User's mailing address Response DTO
 *
 *
 */
@Data
public class MemberAddressRespDTO {

    /**
     * Number
     */
    private Long id;
    /**
     * User Number
     */
    private Long userId;
    /**
     * Recipient Name
     */
    private String name;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Region Code
     */
    private Integer areaId;
    /**
     * Detailed address of delivery
     */
    private String detailAddress;
    /**
     * Whether default
     */
    private Boolean defaultStatus;

}
