package cn.econets.blossom.module.mp.service.menu;

import cn.econets.blossom.module.mp.controller.admin.menu.vo.MpMenuSaveReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.menu.MpMenuDO;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import javax.validation.Valid;
import java.util.List;

/**
 * Official Account Menu Service Interface
 *
 *
 */
public interface MpMenuService {

    /**
     * Save the public account menu
     *
     * @param createReqVO Create information
     */
    void saveMenu(@Valid MpMenuSaveReqVO createReqVO);

    /**
     * Delete the public account menu
     *
     * @param accountId The public account number
     */
    void deleteMenuByAccountId(Long accountId);

    /**
     * When fans click the menu button，Reply to the corresponding message
     *
     * @param appId Public Account AppId
     * @param key Menu button logo
     * @param openid Fans openid
     * @return Message
     */
    WxMpXmlOutMessage reply(String appId, String key, String openid);

    /**
     * Get the public account menu list
     *
     * @param accountId The public account number
     * @return Official Account Menu List
     */
    List<MpMenuDO> getMenuListByAccountId(Long accountId);

}
