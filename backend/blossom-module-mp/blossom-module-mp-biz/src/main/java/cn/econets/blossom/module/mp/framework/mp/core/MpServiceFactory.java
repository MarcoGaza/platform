package cn.econets.blossom.module.mp.framework.mp.core;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.List;

/**
 * {@link WxMpService} Factory interface
 *
 *
 */
public interface MpServiceFactory {

    /**
     * Accounts based on WeChat public accounts，Initialize the corresponding WxMpService with WxMpMessageRouter Example
     *
     * @param list List of public account accounts
     */
    void init(List<MpAccountDO> list);

    /**
     * Get id Corresponding WxMpService Example
     *
     * @param id WeChat public account number
     * @return WxMpService Example
     */
    WxMpService getMpService(Long id);

    default WxMpService getRequiredMpService(Long id) {
        WxMpService wxMpService = getMpService(id);
        Assert.notNull(wxMpService, "Found the corresponding id({}) of WxMpService，Please verify！", id);
        return wxMpService;
    }

    /**
     * Get appId Corresponding WxMpService Example
     *
     * @param appId WeChat public account appId
     * @return WxMpService Example
     */
    WxMpService getMpService(String appId);

    default WxMpService getRequiredMpService(String appId) {
        WxMpService wxMpService = getMpService(appId);
        Assert.notNull(wxMpService, "Found the corresponding appId({}) of WxMpService，Please verify！", appId);
        return wxMpService;
    }

    /**
     * Get appId Corresponding WxMpMessageRouter Example
     *
     * @param appId WeChat public account appId
     * @return WxMpMessageRouter Example
     */
    WxMpMessageRouter getMpMessageRouter(String appId);

    default WxMpMessageRouter getRequiredMpMessageRouter(String appId) {
        WxMpMessageRouter wxMpMessageRouter = getMpMessageRouter(appId);
        Assert.notNull(wxMpMessageRouter, "Found the corresponding appId({}) of WxMpMessageRouter，Please verify！", appId);
        return wxMpMessageRouter;
    }

}
