package cn.econets.blossom.module.system.service.social;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.socail.vo.client.SocialClientPageReqVO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.client.SocialClientSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialClientDO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import com.xingyuv.jushauth.model.AuthUser;
import me.chanjar.weixin.common.bean.WxJsapiSignature;

import javax.validation.Valid;

/**
 * Social Application Service Interface
 *
 */
public interface SocialClientService {

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
     * Request social platform，Authorized user
     *
     * @param socialType Type of social platform
     * @param userType User Type
     * @param code Authorization code
     * @param state Authorization state
     * @return Authorized user
     */
    AuthUser getAuthUser(Integer socialType, Integer userType, String code, String state);

    // =================== Only for WeChat public accounts ===================

    /**
     * Create a WeChat public account JS SDK Signatures required for initialization
     *
     * @param userType User Type
     * @param url Visited URL Address
     * @return Signature
     */
    WxJsapiSignature createWxMpJsapiSignature(Integer userType, String url);

    // =================== Unique to WeChat Mini Programs ===================

    /**
     * Get the mobile phone information of WeChat applet
     *
     * @param userType User Type
     * @param phoneCode Mobile phone authorization code
     * @return Mobile phone information
     */
    WxMaPhoneNumberInfo getWxMaPhoneNumberInfo(Integer userType, String phoneCode);

    // =================== Client Management ===================

    /**
     * Create a social client
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSocialClient(@Valid SocialClientSaveReqVO createReqVO);

    /**
     * Update social client
     *
     * @param updateReqVO Update information
     */
    void updateSocialClient(@Valid SocialClientSaveReqVO updateReqVO);

    /**
     * Delete social client
     *
     * @param id Number
     */
    void deleteSocialClient(Long id);

    /**
     * Get social client
     *
     * @param id Number
     * @return Social client
     */
    SocialClientDO getSocialClient(Long id);

    /**
     * Get social client paging
     *
     * @param pageReqVO Paged query
     * @return Social client paging
     */
    PageResult<SocialClientDO> getSocialClientPage(SocialClientPageReqVO pageReqVO);

}
