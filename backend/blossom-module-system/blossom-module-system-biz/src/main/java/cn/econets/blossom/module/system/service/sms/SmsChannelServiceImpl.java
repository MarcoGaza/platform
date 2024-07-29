package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.cache.CacheUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.framework.sms.core.client.SmsClient;
import cn.econets.blossom.module.system.framework.sms.core.client.SmsClientFactory;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.econets.blossom.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import cn.econets.blossom.module.system.controller.admin.sms.vo.channel.SmsChannelSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.sms.SmsChannelMapper;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.hutool.core.util.StrUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * SMS channel Service Implementation class
 *
 */
@Service
@Slf4j
public class SmsChannelServiceImpl implements SmsChannelService {

    /**
     * {@link SmsClient} Cache，Refresh asynchronously through it smsClientFactory
     */
    @Getter
    private final LoadingCache<Long, SmsClient> idClientCache = CacheUtils.buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Long, SmsClient>() {

                @Override
                public SmsClient load(Long id) {
                    // Query，Then try to refresh
                    SmsChannelDO channel = smsChannelMapper.selectById(id);
                    if (channel != null) {
                        SmsChannelProperties properties = BeanUtils.toBean(channel, SmsChannelProperties.class);
                        smsClientFactory.createOrUpdateSmsClient(properties);
                    }
                    return smsClientFactory.getSmsClient(id);
                }

            });

    /**
     * {@link SmsClient} Cache，Refresh asynchronously through it smsClientFactory
     */
    @Getter
    private final LoadingCache<String, SmsClient> codeClientCache = CacheUtils.buildAsyncReloadingCache(Duration.ofSeconds(60L),
            new CacheLoader<String, SmsClient>() {

                @Override
                public SmsClient load(String code) {
                    // Query，Then try to refresh
                    SmsChannelDO channel = smsChannelMapper.selectByCode(code);
                    if (channel != null) {
                        SmsChannelProperties properties = BeanUtils.toBean(channel, SmsChannelProperties.class);
                        smsClientFactory.createOrUpdateSmsClient(properties);
                    }
                    return smsClientFactory.getSmsClient(code);
                }

            });

    @Resource
    private SmsClientFactory smsClientFactory;

    @Resource
    private SmsChannelMapper smsChannelMapper;

    @Resource
    private SmsTemplateService smsTemplateService;

    @Override
    public Long createSmsChannel(SmsChannelSaveReqVO createReqVO) {
        SmsChannelDO channel = BeanUtils.toBean(createReqVO, SmsChannelDO.class);
        smsChannelMapper.insert(channel);
        return channel.getId();
    }

    @Override
    public void updateSmsChannel(SmsChannelSaveReqVO updateReqVO) {
        // Verify existence
        SmsChannelDO channel = validateSmsChannelExists(updateReqVO.getId());
        // Update
        SmsChannelDO updateObj = BeanUtils.toBean(updateReqVO, SmsChannelDO.class);
        smsChannelMapper.updateById(updateObj);

        // Clear cache
        clearCache(updateReqVO.getId(), channel.getCode());
    }

    @Override
    public void deleteSmsChannel(Long id) {
        // Check existence
        SmsChannelDO channel = validateSmsChannelExists(id);
        // Check whether there is a template using this account
        if (smsTemplateService.getSmsTemplateCountByChannelId(id) > 0) {
            throw exception(ErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN);
        }
        // Delete
        smsChannelMapper.deleteById(id);

        // Clear cache
        clearCache(id, channel.getCode());
    }

    /**
     * Clear the cache of the specified channel number
     *
     * @param id Channel Number
     * @param code Channel Code
     */
    private void clearCache(Long id, String code) {
        idClientCache.invalidate(id);
        if (StrUtil.isNotEmpty(code)) {
            codeClientCache.invalidate(code);
        }
    }

    private SmsChannelDO validateSmsChannelExists(Long id) {
        SmsChannelDO channel = smsChannelMapper.selectById(id);
        if (channel == null) {
            throw exception(ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS);
        }
        return channel;
    }

    @Override
    public SmsChannelDO getSmsChannel(Long id) {
        return smsChannelMapper.selectById(id);
    }

    @Override
    public List<SmsChannelDO> getSmsChannelList() {
        return smsChannelMapper.selectList();
    }

    @Override
    public PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO) {
        return smsChannelMapper.selectPage(pageReqVO);
    }

    @Override
    public SmsClient getSmsClient(Long id) {
        return idClientCache.getUnchecked(id);
    }

    @Override
    public SmsClient getSmsClient(String code) {
        return codeClientCache.getUnchecked(code);
    }

}
