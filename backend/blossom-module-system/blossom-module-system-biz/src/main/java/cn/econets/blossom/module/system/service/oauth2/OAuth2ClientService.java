package cn.econets.blossom.module.system.service.oauth2;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.client.OAuth2ClientSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ClientDO;

import javax.validation.Valid;
import java.util.Collection;

/**
 * OAuth2.0 Client Service Interface
 *
 * Functionally，Japanese JdbcClientDetailsService Function，Provide client operations
 *
 */
public interface OAuth2ClientService {

    /**
     * Create OAuth2 Client
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createOAuth2Client(@Valid OAuth2ClientSaveReqVO createReqVO);

    /**
     * Update OAuth2 Client
     *
     * @param updateReqVO Update information
     */
    void updateOAuth2Client(@Valid OAuth2ClientSaveReqVO updateReqVO);

    /**
     * Delete OAuth2 Client
     *
     * @param id Number
     */
    void deleteOAuth2Client(Long id);

    /**
     * Get OAuth2 Client
     *
     * @param id Number
     * @return OAuth2 Client
     */
    OAuth2ClientDO getOAuth2Client(Long id);

    /**
     * Get OAuth2 Client，From cache
     *
     * @param clientId Client ID
     * @return OAuth2 Client
     */
    OAuth2ClientDO getOAuth2ClientFromCache(String clientId);

    /**
     * Get OAuth2 Client-side paging
     *
     * @param pageReqVO Paged query
     * @return OAuth2 Client-side paging
     */
    PageResult<OAuth2ClientDO> getOAuth2ClientPage(OAuth2ClientPageReqVO pageReqVO);

    /**
     * From cache，Verify whether the client is legal
     *
     * @return Client
     */
    default OAuth2ClientDO validOAuthClientFromCache(String clientId) {
        return validOAuthClientFromCache(clientId, null, null, null, null);
    }

    /**
     * From cache，Verify whether the client is legal
     *
     * When not empty，Perform verification
     *
     * @param clientId Client ID
     * @param clientSecret Client Key
     * @param authorizedGrantType Authorization method
     * @param scopes Authorization scope
     * @param redirectUri Redirect address
     * @return Client
     */
    OAuth2ClientDO validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType,
                                             Collection<String> scopes, String redirectUri);

}
