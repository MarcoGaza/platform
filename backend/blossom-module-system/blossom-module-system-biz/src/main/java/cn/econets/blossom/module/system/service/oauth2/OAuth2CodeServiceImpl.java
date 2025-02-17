package cn.econets.blossom.module.system.service.oauth2;

import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.module.system.dal.mysql.oauth2.OAuth2CodeMapper;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2CodeDO;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.OAUTH2_CODE_EXPIRE;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS;

/**
 * OAuth2.0 Authorization code Service Implementation class
 *
 */
@Service
@Validated
public class OAuth2CodeServiceImpl implements OAuth2CodeService {

    /**
     * Authorization code expiration time，Default 5 Minutes
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Resource
    private OAuth2CodeMapper oauth2CodeMapper;

    @Override
    public OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId,
                                                List<String> scopes, String redirectUri, String state) {
        OAuth2CodeDO codeDO = new OAuth2CodeDO();
        codeDO.setCode(generateCode());
        codeDO.setUserId(userId);
        codeDO.setUserType(userType);
        codeDO.setClientId(clientId);
        codeDO.setScopes(scopes);
        codeDO.setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT));
        codeDO.setRedirectUri(redirectUri);
        codeDO.setState(state);
        oauth2CodeMapper.insert(codeDO);
        return codeDO;
    }

    @Override
    public OAuth2CodeDO consumeAuthorizationCode(String code) {
        OAuth2CodeDO codeDO = oauth2CodeMapper.selectByCode(code);
        if (codeDO == null) {
            throw exception(OAUTH2_CODE_NOT_EXISTS);
        }
        if (DateUtils.isExpired(codeDO.getExpiresTime())) {
            throw exception(OAUTH2_CODE_EXPIRE);
        }
        oauth2CodeMapper.deleteById(codeDO.getId());
        return codeDO;
    }

    private static String generateCode() {
        return IdUtil.fastSimpleUUID();
    }

}
