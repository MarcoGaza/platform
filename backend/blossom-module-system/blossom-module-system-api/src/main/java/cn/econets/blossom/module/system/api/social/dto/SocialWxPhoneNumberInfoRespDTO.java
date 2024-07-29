package cn.econets.blossom.module.system.api.social.dto;

import lombok.Data;

/**
 * Mobile phone information of WeChat applet Response DTO
 *
 */
@Data
public class SocialWxPhoneNumberInfoRespDTO {

    /**
     * Mobile phone number bound by the user（Foreign mobile phone numbers will have area codes）
     */
    private String phoneNumber;

    /**
     * Mobile phone number without area code
     */
    private String purePhoneNumber;
    /**
     * Area code
     */
    private String countryCode;

}
