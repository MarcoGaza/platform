package cn.econets.blossom.module.system.service.oauth2;


import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * OAuth2 Approved Service Interface
 *
 * Functionally，Japanese Spring Security OAuth of ApprovalStoreUserApprovalHandler Function，Record the user's authorization for the specified client，Reduce manual confirmation。
 *
 */
public interface OAuth2ApproveService {

    /**
     * Get the specified user，Specified authorization for a specified client，Passed
     *
     * Reference ApprovalStoreUserApprovalHandler of checkForPreApproval Method
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param requestedScopes Authorization scope
     * @return Is the authorization passed?
     */
    boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes);

    /**
     * When the user initiates approval，Based on scopes Options，Calculate whether it is finally passed
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param requestedScopes Authorization scope
     * @return Is the authorization passed?
     */
    boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes);

    /**
     * Get the user's approval list，Exclude expired
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @return Is the authorization passed?
     */
    List<OAuth2ApproveDO> getApproveList(Long userId, Integer userType, String clientId);

}
