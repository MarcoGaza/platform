package cn.econets.blossom.module.pay.service.channel;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import cn.econets.blossom.framework.pay.core.client.PayClientFactory;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import cn.econets.blossom.module.pay.convert.channel.PayChannelConvert;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.dal.mysql.channel.PayChannelMapper;
import cn.econets.blossom.module.pay.framework.pay.core.WalletPayClient;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Validator;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Payment channel Service Implementation class
 *
 *
 */
@Service
@Slf4j
@Validated
public class PayChannelServiceImpl implements PayChannelService {

    /**
     * {@link PayClient} Cache，Clear it asynchronously smsClientFactory
     */
    @Getter
    private final LoadingCache<Long, PayClient> clientCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Long, PayClient>() {

                @Override
                public PayClient load(Long id) {
                    // Query，Then try to clear
                    PayChannelDO channel = payChannelMapper.selectById(id);
                    if (channel != null) {
                        payClientFactory.createOrUpdatePayClient(channel.getId(), channel.getCode(), channel.getConfig());
                    }
                    return payClientFactory.getPayClient(id);
                }

            });

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayChannelMapper payChannelMapper;

    @Resource
    private Validator validator;

    /**
     * Initialization，To register a wallet
     */
    @PostConstruct
    public void init() {
        payClientFactory.registerPayClientClass(PayChannelEnum.WALLET, WalletPayClient.class);
    }

    @Override
    public Long createChannel(PayChannelCreateReqVO reqVO) {
        // Assert whether there are duplicates
        PayChannelDO dbChannel = getChannelByAppIdAndCode(reqVO.getAppId(), reqVO.getCode());
        if (dbChannel != null) {
            throw exception(CHANNEL_EXIST_SAME_CHANNEL_ERROR);
        }

        // Add new channel
        PayChannelDO channel = PayChannelConvert.INSTANCE.convert(reqVO)
                .setConfig(parseConfig(reqVO.getCode(), reqVO.getConfig()));
        payChannelMapper.insert(channel);
        return channel.getId();
    }

    @Override
    public void updateChannel(PayChannelUpdateReqVO updateReqVO) {
        // Check existence
        PayChannelDO dbChannel = validateChannelExists(updateReqVO.getId());

        // Update
        PayChannelDO channel = PayChannelConvert.INSTANCE.convert(updateReqVO)
                .setConfig(parseConfig(dbChannel.getCode(), updateReqVO.getConfig()));
        payChannelMapper.updateById(channel);

        // Clear cache
        clearCache(channel.getId());
    }

    /**
     * Parse and verify configuration
     *
     * @param code      Channel Code
     * @param configStr Configuration
     * @return Payment Configuration
     */
    private PayClientConfig parseConfig(String code, String configStr) {
        // Parsing configuration
        Class<? extends PayClientConfig> payClass = PayChannelEnum.getByCode(code).getConfigClass();
        if (ObjectUtil.isNull(payClass)) {
            throw exception(CHANNEL_NOT_FOUND);
        }
        PayClientConfig config = JsonUtils.parseObject2(configStr, payClass);
        Assert.notNull(config);

        // Verification parameters
        config.validate(validator);
        return config;
    }

    @Override
    public void deleteChannel(Long id) {
        // Verify existence
        validateChannelExists(id);

        // Delete
        payChannelMapper.deleteById(id);

        // Clear cache
        clearCache(id);
    }

    /**
     * Delete cache
     *
     * @param id Channel Number
     */
    private void clearCache(Long id) {
        clientCache.invalidate(id);
    }

    private PayChannelDO validateChannelExists(Long id) {
        PayChannelDO channel = payChannelMapper.selectById(id);
        if (channel == null) {
            throw exception(CHANNEL_NOT_FOUND);
        }
        return channel;
    }

    @Override
    public PayChannelDO getChannel(Long id) {
        return payChannelMapper.selectById(id);
    }

    @Override
    public List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds) {
        return payChannelMapper.selectListByAppIds(appIds);
    }

    @Override
    public PayChannelDO getChannelByAppIdAndCode(Long appId, String code) {
        return payChannelMapper.selectByAppIdAndCode(appId, code);
    }

    @Override
    public PayChannelDO validPayChannel(Long id) {
        PayChannelDO channel = payChannelMapper.selectById(id);
        validPayChannel(channel);
        return channel;
    }

    @Override
    public PayChannelDO validPayChannel(Long appId, String code) {
        PayChannelDO channel = payChannelMapper.selectByAppIdAndCode(appId, code);
        validPayChannel(channel);
        return channel;
    }

    private void validPayChannel(PayChannelDO channel) {
        if (channel == null) {
            throw exception(CHANNEL_NOT_FOUND);
        }
        if (CommonStatusEnum.DISABLE.getStatus().equals(channel.getStatus())) {
            throw exception(CHANNEL_IS_DISABLE);
        }
    }

    @Override
    public List<PayChannelDO> getEnableChannelList(Long appId) {
        return payChannelMapper.selectListByAppId(appId, CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public PayClient getPayClient(Long id) {
        return clientCache.getUnchecked(id);
    }

}
