package cn.econets.blossom.module.system.service.social;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.cache.CacheUtils;
import cn.econets.blossom.framework.common.util.http.HttpUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.socail.vo.client.SocialClientPageReqVO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.client.SocialClientSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.social.SocialClientMapper;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialClientDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReflectUtil;
import com.binarywang.spring.starter.wxjava.miniapp.properties.WxMaProperties;
import com.binarywang.spring.starter.wxjava.mp.properties.WxMpProperties;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.xingyuv.jushauth.config.AuthConfig;
import com.xingyuv.jushauth.model.AuthCallback;
import com.xingyuv.jushauth.model.AuthResponse;
import com.xingyuv.jushauth.model.AuthUser;
import com.xingyuv.jushauth.request.AuthRequest;
import com.xingyuv.jushauth.utils.AuthStateUtils;
import com.xingyuv.justauth.AuthRequestFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Social Application Service Implementation class
 *
 */
@Service
@Slf4j
public class SocialClientServiceImpl implements SocialClientService {

    @Resource
    private AuthRequestFactory authRequestFactory;

    @Resource
    private WxMpService wxMpService;
    @Resource
    private WxMpProperties wxMpProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate; // WxMpService Need to use，So in Service Injected it
    /**
     * Cache WxMpService Object
     *
     * key：Using WeChat public account appId + secret Splicing，That is {@link SocialClientDO} of clientId Japanese clientSecret Properties。
     * Why key Use this format？Because {@link SocialClientDO} Can be changed in the management backend，Through this key Store its singleton。
     *
     * Why do it? WxMpService Cache？Because WxMpService The construction cost is relatively high，So try to ensure it is a singleton。
     */
    private final LoadingCache<String, WxMpService> wxMpServiceCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofSeconds(10L),
            new CacheLoader<String, WxMpService>() {

                @Override
                public WxMpService load(String key) {
                    String[] keys = key.split(":");
                    return buildWxMpService(keys[0], keys[1]);
                }

            });

    @Resource
    private WxMaService wxMaService;
    @Resource
    private WxMaProperties wxMaProperties;
    /**
     * Cache WxMaService Object
     *
     * Same as description {@link #wxMpServiceCache} Variable
     */
    private final LoadingCache<String, WxMaService> wxMaServiceCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofSeconds(10L),
            new CacheLoader<String, WxMaService>() {

                @Override
                public WxMaService load(String key) {
                    String[] keys = key.split(":");
                    return buildWxMaService(keys[0], keys[1]);
                }

            });

    @Resource
    private SocialClientMapper socialClientMapper;

    @Override
    public String getAuthorizeUrl(Integer socialType, Integer userType, String redirectUri) {
        // Get the corresponding AuthRequest Realization
        AuthRequest authRequest = buildAuthRequest(socialType, userType);
        // Generate jump address
        String authorizeUri = authRequest.authorize(AuthStateUtils.createState());
        return HttpUtils.replaceUrlQuery(authorizeUri, "redirect_uri", redirectUri);
    }

    @Override
    public AuthUser getAuthUser(Integer socialType, Integer userType, String code, String state) {
        // Build request
        AuthRequest authRequest = buildAuthRequest(socialType, userType);
        AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
        // Execute request
        AuthResponse<?> authResponse = authRequest.login(authCallback);
        log.info("[getAuthUser][Request social platform type({}) request({}) response({})]", socialType,
                JsonUtils.toJsonString(authCallback), JsonUtils.toJsonString(authResponse));
        if (!authResponse.ok()) {
            throw exception(ErrorCodeConstants.SOCIAL_USER_AUTH_FAILURE, authResponse.getMsg());
        }
        return (AuthUser) authResponse.getData();
    }

    /**
     * Build AuthRequest Object，Support multi-tenant configuration
     *
     * @param socialType Social Type
     * @param userType User Type
     * @return AuthRequest Object
     */
    @VisibleForTesting
    AuthRequest buildAuthRequest(Integer socialType, Integer userType) {
        // 1. Find the default configuration items first，From application-*.yaml Read in
        AuthRequest request = authRequestFactory.get(SocialTypeEnum.valueOfType(socialType).getSource());
        Assert.notNull(request, String.format("Social platform(%d) Does not exist", socialType));
        // 2. Query DB Configuration items，Overwrite if it exists
        SocialClientDO client = socialClientMapper.selectBySocialTypeAndUserType(socialType, userType);
        if (client != null && Objects.equals(client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            // 2.1 Construct new AuthConfig Object
            AuthConfig authConfig = (AuthConfig) ReflectUtil.getFieldValue(request, "config");
            AuthConfig newAuthConfig = ReflectUtil.newInstance(authConfig.getClass());
            BeanUtil.copyProperties(authConfig, newAuthConfig);
            // 2.2 Modify the corresponding clientId + clientSecret Key
            newAuthConfig.setClientId(client.getClientId());
            newAuthConfig.setClientSecret(client.getClientSecret());
            if (client.getAgentId() != null) { // If there is one agentId Modify agentId
                newAuthConfig.setAgentId(client.getAgentId());
            }
            // 2.3 Settings request Sato，For subsequent use
            ReflectUtil.setFieldValue(request, "config", newAuthConfig);
        }
        return request;
    }

    // =================== Only for WeChat public accounts ===================

    @Override
    @SneakyThrows
    public WxJsapiSignature createWxMpJsapiSignature(Integer userType, String url) {
        WxMpService service = getWxMpService(userType);
        return service.createJsapiSignature(url);
    }

    /**
     * Get clientId + clientSecret Corresponding WxMpService Object
     *
     * @param userType User Type
     * @return WxMpService Object
     */
    @VisibleForTesting
    WxMpService getWxMpService(Integer userType) {
        // First step，Query DB Configuration items，Get the corresponding WxMpService Object
        SocialClientDO client = socialClientMapper.selectBySocialTypeAndUserType(
                SocialTypeEnum.WECHAT_MP.getType(), userType);
        if (client != null && Objects.equals(client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            return wxMpServiceCache.getUnchecked(client.getClientId() + ":" + client.getClientSecret());
        }
        // Step 2，Does not exist DB Configuration item，Then use application-*.yaml Corresponding WxMpService Object
        return wxMpService;
    }

    /**
     * Create clientId + clientSecret Corresponding WxMpService Object
     *
     * @param clientId WeChat public account appId
     * @param clientSecret WeChat public account secret
     * @return WxMpService Object
     */
    public WxMpService buildWxMpService(String clientId, String clientSecret) {
        // First step，Create WxMpRedisConfigImpl Object
        WxMpRedisConfigImpl configStorage = new WxMpRedisConfigImpl(
                new RedisTemplateWxRedisOps(stringRedisTemplate),
                wxMpProperties.getConfigStorage().getKeyPrefix());
        configStorage.setAppId(clientId);
        configStorage.setSecret(clientSecret);

        // Step 2，Create WxMpService Object
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }

    // =================== Unique to WeChat Mini Programs ===================

    @Override
    public WxMaPhoneNumberInfo getWxMaPhoneNumberInfo(Integer userType, String phoneCode) {
        WxMaService service = getWxMaService(userType);
        try {
            return service.getUserService().getPhoneNoInfo(phoneCode);
        } catch (WxErrorException e) {
            log.error("[getPhoneNoInfo][userType({}) phoneCode({}) Failed to obtain mobile phone number]", userType, phoneCode, e);
            throw exception(ErrorCodeConstants.SOCIAL_CLIENT_WEIXIN_MINI_APP_PHONE_CODE_ERROR);
        }
    }

    /**
     * Get clientId + clientSecret Corresponding WxMpService Object
     *
     * @param userType User Type
     * @return WxMpService Object
     */
    @VisibleForTesting
    WxMaService getWxMaService(Integer userType) {
        // First step，Query DB Configuration items，Get the corresponding WxMaService Object
        SocialClientDO client = socialClientMapper.selectBySocialTypeAndUserType(
                SocialTypeEnum.WECHAT_MINI_APP.getType(), userType);
        if (client != null && Objects.equals(client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            return wxMaServiceCache.getUnchecked(client.getClientId() + ":" + client.getClientSecret());
        }
        // Step 2，Does not exist DB Configuration item，Then use application-*.yaml Corresponding WxMaService Object
        return wxMaService;
    }

    /**
     * Create clientId + clientSecret Corresponding WxMaService Object
     *
     * @param clientId WeChat Mini Program appId
     * @param clientSecret WeChat Mini Program secret
     * @return WxMaService Object
     */
    private WxMaService buildWxMaService(String clientId, String clientSecret) {
        // First step，Create WxMaRedisBetterConfigImpl Object
        WxMaRedisBetterConfigImpl configStorage = new WxMaRedisBetterConfigImpl(
                new RedisTemplateWxRedisOps(stringRedisTemplate),
                wxMaProperties.getConfigStorage().getKeyPrefix());
        configStorage.setAppid(clientId);
        configStorage.setSecret(clientSecret);

        // Step 2，Create WxMpService Object
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(configStorage);
        return service;
    }

    // =================== Client Management ===================

    @Override
    public Long createSocialClient(SocialClientSaveReqVO createReqVO) {
        // Check for duplicates
        validateSocialClientUnique(null, createReqVO.getUserType(), createReqVO.getSocialType());

        // Insert
        SocialClientDO client = BeanUtils.toBean(createReqVO, SocialClientDO.class);
        socialClientMapper.insert(client);
        return client.getId();
    }

    @Override
    public void updateSocialClient(SocialClientSaveReqVO updateReqVO) {
        // Verify existence
        validateSocialClientExists(updateReqVO.getId());
        // Check for duplicates
        validateSocialClientUnique(updateReqVO.getId(), updateReqVO.getUserType(), updateReqVO.getSocialType());

        // Update
        SocialClientDO updateObj = BeanUtils.toBean(updateReqVO, SocialClientDO.class);
        socialClientMapper.updateById(updateObj);
    }

    @Override
    public void deleteSocialClient(Long id) {
        // Check existence
        validateSocialClientExists(id);
        // Delete
        socialClientMapper.deleteById(id);
    }

    private void validateSocialClientExists(Long id) {
        if (socialClientMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SOCIAL_CLIENT_NOT_EXISTS);
        }
    }

    /**
     * Check if social applications are duplicated，Guarantee required userType + socialType Only
     *
     * The reason is，Different ends（userType）Select a social login（socialType）Time，Need to pass {@link #buildAuthRequest(Integer, Integer)} Build the corresponding request
     *
     * @param id Number
     * @param userType User Type
     * @param socialType Social Type
     */
    private void validateSocialClientUnique(Long id, Integer userType, Integer socialType) {
        SocialClientDO client = socialClientMapper.selectBySocialTypeAndUserType(
                socialType, userType);
        if (client == null) {
            return;
        }
        if (id == null // When adding，Description repeated
                || ObjUtil.notEqual(id, client.getId())) { // Updating，If id Inconsistent，Description repeated
            throw exception(ErrorCodeConstants.SOCIAL_CLIENT_UNIQUE);
        }
    }

    @Override
    public SocialClientDO getSocialClient(Long id) {
        return socialClientMapper.selectById(id);
    }

    @Override
    public PageResult<SocialClientDO> getSocialClientPage(SocialClientPageReqVO pageReqVO) {
        return socialClientMapper.selectPage(pageReqVO);
    }

}
