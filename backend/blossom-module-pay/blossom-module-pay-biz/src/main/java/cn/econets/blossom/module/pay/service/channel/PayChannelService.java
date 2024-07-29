package cn.econets.blossom.module.pay.service.channel;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Payment channel Service Interface
 *
 *
 */
public interface PayChannelService {

    /**
     * Create a payment channel
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createChannel(@Valid PayChannelCreateReqVO createReqVO);

    /**
     * Update payment channel
     *
     * @param updateReqVO Update information
     */
    void updateChannel(@Valid PayChannelUpdateReqVO updateReqVO);

    /**
     * Delete payment channel
     *
     * @param id Number
     */
    void deleteChannel(Long id);

    /**
     * Get payment channel
     *
     * @param id Number
     * @return Payment channel
     */
    PayChannelDO getChannel(Long id);

    /**
     * Based on payment application ID Collection，Get the payment channel list
     *
     * @param appIds Application ID Collection
     * @return Payment channel list
     */
    List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds);

    /**
     * Get channels based on conditions
     *
     * @param appId      Application number
     * @param code       Channel Code
     * @return Quantity
     */
    PayChannelDO getChannelByAppIdAndCode(Long appId, String code);

    /**
     * Legitimacy of payment channels
     *
     * If it is illegal，Throw {@link ServiceException} Business exception
     *
     * @param id Channel Number
     * @return Channel Information
     */
    PayChannelDO validPayChannel(Long id);

    /**
     * Legitimacy of payment channels
     *
     * If it is illegal，Throw {@link ServiceException} Business exception
     *
     * @param appId Application number
     * @param code Payment channel
     * @return Channel Information
     */
    PayChannelDO validPayChannel(Long appId, String code);

    /**
     * Get the list of enabled channels for the specified application
     *
     * @param appId Application number
     * @return Channel List
     */
    List<PayChannelDO> getEnableChannelList(Long appId);

    /**
     * Get the payment client with the specified ID
     *
     * @param id Number
     * @return Payment Client
     */
    PayClient getPayClient(Long id);

}
