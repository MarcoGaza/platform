package cn.econets.blossom.module.system.api.social;

import cn.econets.blossom.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;

/**
 * Social applications API Interface
 *
 */
public interface SocialClientApi {

    /**
     * Obtain authorization from social platforms URL
     *
     * @param socialType Type of social platform {@link SocialTypeEnum}
     * @param userType User Type
     * @param redirectUri Redirect URL
     * @return Authorization of social platforms URL
     */
    String getAuthorizeUrl(Integer socialType, Integer userType, String redirectUri);

    /**
     * Create a WeChat public account JS SDK Signatures required for initialization
     *
     * @param userType User Type
     * @param url Visited URL Address
     * @return Signature
     */
    SocialWxJsapiSignatureRespDTO createWxMpJsapiSignature(Integer userType, String url);

    /**
     * Get the mobile phone information of WeChat applet
     *
     * @param userType User Type
     * @param phoneCode Mobile phone authorization code
     * @return Mobile phone information
     */
    SocialWxPhoneNumberInfoRespDTO getWxMaPhoneNumberInfo(Integer userType, String phoneCode);

}
