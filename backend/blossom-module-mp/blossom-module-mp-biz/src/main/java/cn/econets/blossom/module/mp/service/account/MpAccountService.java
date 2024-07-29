package cn.econets.blossom.module.mp.service.account;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.account.vo.MpAccountUpdateReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;

import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.ACCOUNT_NOT_EXISTS;

/**
 * Public Account Service Interface
 *
 *
 */
public interface MpAccountService {

    /**
     * Initialize cache
     */
    void initLocalCache();

    /**
     * Create a public account
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createAccount(@Valid MpAccountCreateReqVO createReqVO);

    /**
     * Update the public account
     *
     * @param updateReqVO Update information
     */
    void updateAccount(@Valid MpAccountUpdateReqVO updateReqVO);

    /**
     * Delete the public account
     *
     * @param id Number
     */
    void deleteAccount(Long id);

    /**
     * Get the public account
     *
     * @param id Number
     * @return Public Account
     */
    MpAccountDO getAccount(Long id);

    /**
     * Get the public account。If it does not exist，Throws a business exception
     *
     * @param id Number
     * @return Public Account
     */
    default MpAccountDO getRequiredAccount(Long id) {
        MpAccountDO account = getAccount(id);
        if (account == null) {
            throw exception(ACCOUNT_NOT_EXISTS);
        }
        return account;
    }

    /**
     * From cache，Get the public account
     *
     * @param appId WeChat public account appId
     * @return Public Account
     */
    MpAccountDO getAccountFromCache(String appId);

    /**
     * Get the public account paging
     *
     * @param pageReqVO Paged query
     * @return Paging of public account
     */
    PageResult<MpAccountDO> getAccountPage(MpAccountPageReqVO pageReqVO);

    /**
     * Get the public account list
     *
     * @return Public account list
     */
    List<MpAccountDO> getAccountList();

    /**
     * Generate a QR code for a public account
     *
     * @param id Number
     */
    void generateAccountQrCode(Long id);

    /**
     * Clear the public account API Quota
     *
     * Reference Document：<a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/API_Call_Limits.html">Interface call frequency limit description</a>
     *
     * @param id Number
     */
    void clearAccountQuota(Long id);

}
