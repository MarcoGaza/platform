package cn.econets.blossom.module.system.api.social.dto;

import lombok.Data;

/**
 * WeChat public account JSAPI Signature Response DTO
 *
 */
@Data
public class SocialWxJsapiSignatureRespDTO {

    /**
     * WeChat public account appId
     */
    private String appId;
    /**
     * Anonymous string
     */
    private String nonceStr;
    /**
     * Timestamp
     */
    private Long timestamp;
    /**
     * URL
     */
    private String url;
    /**
     * Signature
     */
    private String signature;

}
