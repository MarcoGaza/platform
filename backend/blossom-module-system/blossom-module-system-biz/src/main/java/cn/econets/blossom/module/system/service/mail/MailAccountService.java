package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.econets.blossom.module.system.controller.admin.mail.vo.account.MailAccountSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailAccountDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Email Account Service Interface
 *
 */
public interface MailAccountService {

    /**
     * Create an email account
     *
     * @param createReqVO Email account information
     * @return Number
     */
    Long createMailAccount(@Valid MailAccountSaveReqVO createReqVO);

    /**
     * Modify email account
     *
     * @param updateReqVO Email account information
     */
    void updateMailAccount(@Valid MailAccountSaveReqVO updateReqVO);

    /**
     * Delete email account
     *
     * @param id Number
     */
    void deleteMailAccount(Long id);

    /**
     * Get email account information
     *
     * @param id Number
     * @return Email account information
     */
    MailAccountDO getMailAccount(Long id);

    /**
     * Get the email account from the cache
     *
     * @param id Number
     * @return Email Account
     */
    MailAccountDO getMailAccountFromCache(Long id);

    /**
     * Get email account paging information
     *
     * @param pageReqVO Email account paging parameters
     * @return Mailbox account paging information
     */
    PageResult<MailAccountDO> getMailAccountPage(MailAccountPageReqVO pageReqVO);

    /**
     * Get mailbox array information
     *
     * @return Email account information array
     */
    List<MailAccountDO> getMailAccountList();

}
