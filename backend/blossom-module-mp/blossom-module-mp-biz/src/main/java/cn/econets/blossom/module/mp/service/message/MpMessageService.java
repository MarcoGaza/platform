package cn.econets.blossom.module.mp.service.message;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessageSendReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import cn.econets.blossom.module.mp.service.message.bo.MpMessageSendOutReqBO;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import javax.validation.Valid;

/**
 * Public account news Service Interface
 *
 *
 */
public interface MpMessageService {

    /**
     * Get the public account message paging
     *
     * @param pageReqVO Paged query
     * @return Paging of public account messages
     */
    PageResult<MpMessageDO> getMessagePage(MpMessagePageReqVO pageReqVO);

    /**
     * From the public account，Received fan message
     *
     * @param appId WeChat public account appId
     * @param wxMessage Message
     */
    void receiveMessage(String appId, WxMpXmlMessage wxMessage);

    /**
     * Use the public account，Reply to fans
     *
     * For example：Automatic reply、Customer Service Message、Menu reply message and other scenarios
     *
     * Attention，This method just returns WxMpXmlOutMessage Object，Will not actually send the message
     *
     * @param sendReqBO Message content
     * @return WeChat reply message XML
     */
    WxMpXmlOutMessage sendOutMessage(@Valid MpMessageSendOutReqBO sendReqBO);

    /**
     * Use the public account，Send to fans【Customer Service】Message
     *
     * Attention，This method will actually send the message
     *
     * @param sendReqVO Message content
     * @return Message
     */
    MpMessageDO sendKefuMessage(MpMessageSendReqVO sendReqVO);

}
