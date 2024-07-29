package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.framework.sms.core.client.SmsClient;
import cn.econets.blossom.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import cn.econets.blossom.module.system.controller.admin.sms.vo.channel.SmsChannelSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsChannelDO;

import javax.validation.Valid;
import java.util.List;

/**
 * SMS channel Service Interface
 *
 */
public interface SmsChannelService {

    /**
     * Create SMS channel
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSmsChannel(@Valid SmsChannelSaveReqVO createReqVO);

    /**
     * Update SMS channel
     *
     * @param updateReqVO Update information
     */
    void updateSmsChannel(@Valid SmsChannelSaveReqVO updateReqVO);

    /**
     * Delete SMS channel
     *
     * @param id Number
     */
    void deleteSmsChannel(Long id);

    /**
     * Get SMS channel
     *
     * @param id Number
     * @return SMS channel
     */
    SmsChannelDO getSmsChannel(Long id);

    /**
     * Get a list of all SMS channels
     *
     * @return SMS channel list
     */
    List<SmsChannelDO> getSmsChannelList();

    /**
     * Get SMS channel paging
     *
     * @param pageReqVO Paged query
     * @return SMS channel paging
     */
    PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO);

    /**
     * Get SMS client
     *
     * @param id Number
     * @return SMS client
     */
    SmsClient getSmsClient(Long id);

    /**
     * Get SMS client
     *
     * @param code Encoding
     * @return SMS Client
     */
    SmsClient getSmsClient(String code);

}
