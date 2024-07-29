package cn.econets.blossom.module.mp.service.account;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountUpdateReqVO;
import cn.econets.blossom.module.mp.convert.account.MpAccountConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.mysql.account.MpAccountMapper;
import cn.econets.blossom.module.mp.enums.ErrorCodeConstants;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.getMaxValue;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;

/**
 * Public Account Service Implementation class
 *
 *
 */
@Slf4j
@Service
@Validated
public class MpAccountServiceImpl implements MpAccountService {

    /**
     * Account cache
     * key：Account Number {@link MpAccountDO#getAppId()}
     *
     * Here is the statement volatile The reason for modification is，Every time you refresh，Directly modify the point
     */
    @Getter
    private volatile Map<String, MpAccountDO> accountCache;

    @Resource
    private MpAccountMapper mpAccountMapper;

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpServiceFactory mpServiceFactory;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // Attention：Ignore automatic multi-tenancy，Because the cache needs to be initialized globally
        TenantUtils.executeIgnore(() -> {
            // First step：Query data
            List<MpAccountDO> accounts = Collections.emptyList();
            try {
                accounts = mpAccountMapper.selectList();
            } catch (Throwable ex) {
                if (!ex.getMessage().contains("doesn't exist")) {
                    throw ex;
                }
                log.error("[WeChat public account blossom-module-mp - Table structure not imported][Reference https://doc.econets.cn/pages/4295f6/ Open]");
            }
            log.info("[initLocalCacheIfUpdate][Cache public account，The quantity is:{}]", accounts.size());

            // Step 2：Build Cache。Create or update payment Client
            mpServiceFactory.init(accounts);
            accountCache = convertMap(accounts, MpAccountDO::getAppId);
        });
    }

    /**
     * Polling through scheduled tasks，Refresh cache
     *
     * Purpose：Multi-node deployment，Through polling”Notification“All nodes，Refresh
     */
    @Scheduled(initialDelay = 60, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void refreshLocalCache() {
        // Attention：Ignore automatic multi-tenancy，Because the cache needs to be initialized globally
        TenantUtils.executeIgnore(() -> {
            // Situation 1：If there is no data in the cache，Refresh the cache directly
            if (CollUtil.isEmpty(accountCache)) {
                initLocalCache();
                return;
            }

            // Case 2，If the data is in the cache，Passed updateTime Judge whether there is data change，Refresh cache if there is a change
            LocalDateTime maxTime = getMaxValue(accountCache.values(), MpAccountDO::getUpdateTime);
            if (mpAccountMapper.selectCountByUpdateTimeGt(maxTime) > 0) {
                initLocalCache();
            }
        });
    }

    @Override
    public Long createAccount(MpAccountCreateReqVO createReqVO) {
        // Verification appId Only
        validateAppIdUnique(null, createReqVO.getAppId());

        // Insert
        MpAccountDO account = MpAccountConvert.INSTANCE.convert(createReqVO);
        mpAccountMapper.insert(account);

        // Refresh cache
        initLocalCache();
        return account.getId();
    }

    @Override
    public void updateAccount(MpAccountUpdateReqVO updateReqVO) {
        // Check existence
        validateAccountExists(updateReqVO.getId());
        // Verification appId Only
        validateAppIdUnique(updateReqVO.getId(), updateReqVO.getAppId());

        // Update
        MpAccountDO updateObj = MpAccountConvert.INSTANCE.convert(updateReqVO);
        mpAccountMapper.updateById(updateObj);

        // Refresh cache
        initLocalCache();
    }

    @Override
    public void deleteAccount(Long id) {
        // Check existence
        validateAccountExists(id);
        // Delete
        mpAccountMapper.deleteById(id);

        // Refresh cache
        initLocalCache();
    }

    private MpAccountDO validateAccountExists(Long id) {
        MpAccountDO account = mpAccountMapper.selectById(id);
        if (account == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.ACCOUNT_NOT_EXISTS);
        }
        return account;
    }

    @VisibleForTesting
    public void validateAppIdUnique(Long id, String appId) {
        // Multiple tenants，appId Cannot be repeated，Otherwise the public account callback will not be recognized
        TenantUtils.executeIgnore(() -> {
            MpAccountDO account = mpAccountMapper.selectByAppId(appId);
            if (account == null) {
                return;
            }
            // Exists account Recorded case
            if (id == null // When adding，Description repeated
                    || ObjUtil.notEqual(id, account.getId())) { // Updating，If id Inconsistent，Description repeated
                throw exception(USER_USERNAME_EXISTS);
            }
        });
    }

    @Override
    public MpAccountDO getAccount(Long id) {
        return mpAccountMapper.selectById(id);
    }

    @Override
    public MpAccountDO getAccountFromCache(String appId) {
        return accountCache.get(appId);
    }

    @Override
    public PageResult<MpAccountDO> getAccountPage(MpAccountPageReqVO pageReqVO) {
        return mpAccountMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MpAccountDO> getAccountList() {
        return mpAccountMapper.selectList();
    }

    @Override
    public void generateAccountQrCode(Long id) {
        // Check existence
        MpAccountDO account = validateAccountExists(id);

        // Generate QR code
        WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getAppId());
        String qrCodeUrl;
        try {
            WxMpQrCodeTicket qrCodeTicket = mpService.getQrcodeService().qrCodeCreateLastTicket("default");
            qrCodeUrl = mpService.getQrcodeService().qrCodePictureUrl(qrCodeTicket.getTicket());
        } catch (WxErrorException e) {
            throw exception(ErrorCodeConstants.ACCOUNT_GENERATE_QR_CODE_FAIL, e.getError().getErrorMsg());
        }

        // Save QR code
        mpAccountMapper.updateById(new MpAccountDO().setId(id).setQrCodeUrl(qrCodeUrl));
    }

    @Override
    public void clearAccountQuota(Long id) {
        // Check existence
        MpAccountDO account = validateAccountExists(id);

        // Generate QR code
        WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getAppId());
        try {
            mpService.clearQuota(account.getAppId());
        } catch (WxErrorException e) {
            throw exception(ErrorCodeConstants.ACCOUNT_CLEAR_QUOTA_FAIL, e.getError().getErrorMsg());
        }
    }

}
