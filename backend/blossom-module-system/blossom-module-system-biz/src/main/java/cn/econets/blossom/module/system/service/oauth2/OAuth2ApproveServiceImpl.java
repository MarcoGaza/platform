package cn.econets.blossom.module.system.service.oauth2;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.module.system.dal.mysql.oauth2.OAuth2ApproveMapper;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * OAuth2 Approved Service Implementation class
 *
 */
@Service
@Validated
public class OAuth2ApproveServiceImpl implements OAuth2ApproveService {

    /**
     * Approved expiration date，Default 30 Sky
     */
    private static final Integer TIMEOUT = 30 * 24 * 60 * 60; // Unit：seconds

    @Resource
    private OAuth2ClientService oauth2ClientService;

    @Resource
    private OAuth2ApproveMapper oauth2ApproveMapper;

    @Override
    @Transactional
    public boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes) {
        // First step，Based on Client Automatic authorization calculation，If scopes All are being automatically authorized，Then return true Passed
        OAuth2ClientDO clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        Assert.notNull(clientDO, "Client cannot be empty"); // Defensive Programming
        if (CollUtil.containsAll(clientDO.getAutoApproveScopes(), requestedScopes)) {
            // gh-877 - if all scopes are auto approved, approvals still need to be added to the approval store.
            LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
            for (String scope : requestedScopes) {
                saveApprove(userId, userType, clientId, scope, true, expireTime);
            }
            return true;
        }

        // Step 2，Counting the authorizations that the user has approved。If scopes All included，Then return true
        List<OAuth2ApproveDO> approveDOs = getApproveList(userId, userType, clientId);
        Set<String> scopes = CollectionUtils.convertSet(approveDOs, OAuth2ApproveDO::getScope,
                OAuth2ApproveDO::getApproved); // Only keep unexpired ones + Agreed
        return CollUtil.containsAll(scopes, requestedScopes);
    }

    @Override
    @Transactional
    public boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes) {
        // If requestedScopes Empty，Indicates no requirements，Then return true Passed
        if (CollUtil.isEmpty(requestedScopes)) {
            return true;
        }

        // Update approved information
        boolean success = false; // Requires at least one consent
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
        for (Map.Entry<String, Boolean> entry : requestedScopes.entrySet()) {
            if (entry.getValue()) {
                success = true;
            }
            saveApprove(userId, userType, clientId, entry.getKey(), entry.getValue(), expireTime);
        }
        return success;
    }

    @Override
    public List<OAuth2ApproveDO> getApproveList(Long userId, Integer userType, String clientId) {
        List<OAuth2ApproveDO> approveDOs = oauth2ApproveMapper.selectListByUserIdAndUserTypeAndClientId(
                userId, userType, clientId);
        approveDOs.removeIf(o -> DateUtils.isExpired(o.getExpiresTime()));
        return approveDOs;
    }

    @VisibleForTesting
    void saveApprove(Long userId, Integer userType, String clientId,
                     String scope, Boolean approved, LocalDateTime expireTime) {
        // Update first
        OAuth2ApproveDO approveDO = new OAuth2ApproveDO();
        approveDO.setUserId(userId);
        approveDO.setUserType(userType);
        approveDO.setClientId(clientId);
        approveDO.setScope(scope);
        approveDO.setApproved(approved);
        approveDO.setExpiresTime(expireTime);
        if (oauth2ApproveMapper.update(approveDO) == 1) {
            return;
        }
        // Failed，It means it does not exist，Update
        oauth2ApproveMapper.insert(approveDO);
    }

}
