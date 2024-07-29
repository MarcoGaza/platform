package cn.econets.blossom.module.mp.service.message;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyUpdateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpAutoReplyDO;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * Automatic reply of public account Service Interface
 *
 *
 */
public interface MpAutoReplyService {

    /**
     * Get the automatic reply paging of the public account
     *
     * @param pageVO Pagination request
     * @return Automatically reply to paginated results
     */
    PageResult<MpAutoReplyDO> getAutoReplyPage(MpMessagePageReqVO pageVO);

    /**
     * Get automatic reply from the public account
     *
     * @param id Number
     * @return Auto reply
     */
    MpAutoReplyDO getAutoReply(Long id);


    /**
     * Create an automatic reply to a public account
     *
     * @param createReqVO Create request
     * @return Automatic reply number
     */
    Long createAutoReply(MpAutoReplyCreateReqVO createReqVO);

    /**
     * Update the automatic reply of the public account
     *
     * @param updateReqVO Update request
     */
    void updateAutoReply(MpAutoReplyUpdateReqVO updateReqVO);

    /**
     * Delete the automatic reply of the public account
     *
     * @param id Automatic reply number
     */
    void deleteAutoReply(Long id);

    /**
     * When receiving a message，Auto reply
     *
     * @param appId WeChat public account appId
     * @param wxMessage Message
     * @return Reply message
     */
    WxMpXmlOutMessage replyForMessage(String appId, WxMpXmlMessage wxMessage);

    /**
     * When fans pay attention，Automatic reply
     *
     * @param appId WeChat public account appId
     * @param wxMessage Message
     * @return Reply message
     */
    WxMpXmlOutMessage replyForSubscribe(String appId, WxMpXmlMessage wxMessage);

}
