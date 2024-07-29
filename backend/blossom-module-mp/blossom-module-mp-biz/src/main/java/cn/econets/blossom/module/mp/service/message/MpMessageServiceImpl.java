package cn.econets.blossom.module.mp.service.message;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessageSendReqVO;
import cn.econets.blossom.module.mp.convert.message.MpMessageConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import cn.econets.blossom.module.mp.dal.dataobject.user.MpUserDO;
import cn.econets.blossom.module.mp.dal.mysql.message.MpMessageMapper;
import cn.econets.blossom.module.mp.enums.message.MpMessageSendFromEnum;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import cn.econets.blossom.module.mp.service.material.MpMaterialService;
import cn.econets.blossom.module.mp.service.message.bo.MpMessageSendOutReqBO;
import cn.econets.blossom.module.mp.service.user.MpUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.MESSAGE_SEND_FAIL;

/**
 * Fans message Service Implementation class
 *
 *
 */
@Service
@Validated
@Slf4j
public class MpMessageServiceImpl implements MpMessageService {

    @Resource
    @Lazy // Delayed loading，Avoid circular dependencies
    private MpAccountService mpAccountService;
    @Resource
    private MpUserService mpUserService;
    @Resource
    private MpMaterialService mpMaterialService;

    @Resource
    private MpMessageMapper mpMessageMapper;

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpServiceFactory mpServiceFactory;

    @Resource
    private Validator validator;

    @Override
    public PageResult<MpMessageDO> getMessagePage(MpMessagePageReqVO pageReqVO) {
        return mpMessageMapper.selectPage(pageReqVO);
    }

    @Override
    public void receiveMessage(String appId, WxMpXmlMessage wxMessage) {
        // Get related information
        MpAccountDO account = mpAccountService.getAccountFromCache(appId);
        Assert.notNull(account, "Public Account({}) Does not exist", appId);

        // Subscription events are not recorded，Because there is no data of this fan in the public account fan table at this time
        // TODO ：This fix，See if there are any more problems later
        if (ObjUtil.equal(wxMessage.getEvent(), WxConsts.EventType.SUBSCRIBE)) {
            return;
        }

        MpUserDO user = mpUserService.getUser(appId, wxMessage.getFromUser());
        Assert.notNull(user, "Public Account Fans({}/{}) Does not exist", appId, wxMessage.getFromUser());

        // Record message
        MpMessageDO message = MpMessageConvert.INSTANCE.convert(wxMessage, account, user)
                .setSendFrom(MpMessageSendFromEnum.USER_TO_MP.getFrom());
        downloadMessageMedia(message);
        mpMessageMapper.insert(message);
    }

    @Override
    public WxMpXmlOutMessage sendOutMessage(MpMessageSendOutReqBO sendReqBO) {
        // Verify message format
        MpUtils.validateMessage(validator, sendReqBO.getType(), sendReqBO);

        // Get related information
        MpAccountDO account = mpAccountService.getAccountFromCache(sendReqBO.getAppId());
        Assert.notNull(account, "Public Account({}) Does not exist", sendReqBO.getAppId());
        MpUserDO user = mpUserService.getUser(sendReqBO.getAppId(), sendReqBO.getOpenid());
        Assert.notNull(user, "Public Account Fans({}/{}) Does not exist", sendReqBO.getAppId(), sendReqBO.getOpenid());

        // Record message
        MpMessageDO message = MpMessageConvert.INSTANCE.convert(sendReqBO, account, user).
                setSendFrom(MpMessageSendFromEnum.MP_TO_USER.getFrom());
        downloadMessageMedia(message);
        mpMessageMapper.insert(message);

        // Convert and return WxMpXmlOutMessage Object
        return MpMessageConvert.INSTANCE.convert02(message, account);
    }

    @Override
    public MpMessageDO sendKefuMessage(MpMessageSendReqVO sendReqVO) {
        // Verify message format
        MpUtils.validateMessage(validator, sendReqVO.getType(), sendReqVO);

        // Get related information
        MpUserDO user = mpUserService.getRequiredUser(sendReqVO.getUserId());
        MpAccountDO account = mpAccountService.getRequiredAccount(user.getAccountId());

        // Send customer service message
        WxMpKefuMessage wxMessage = MpMessageConvert.INSTANCE.convert(sendReqVO, user);
        WxMpService mpService = mpServiceFactory.getRequiredMpService(user.getAppId());
        try {
            mpService.getKefuService().sendKefuMessageWithResponse(wxMessage);
        } catch (WxErrorException e) {
            throw exception(MESSAGE_SEND_FAIL, e.getError().getErrorMsg());
        }

        // Record message
        MpMessageDO message = MpMessageConvert.INSTANCE.convert(wxMessage, account, user)
                .setSendFrom(MpMessageSendFromEnum.MP_TO_USER.getFrom());
        downloadMessageMedia(message);
        mpMessageMapper.insert(message);
        return message;
    }

    /**
     * Download the media files used in the message，and upload to the file service
     *
     * @param message Message
     */
    private void downloadMessageMedia(MpMessageDO message) {
        if (StrUtil.isNotEmpty(message.getMediaId())) {
            message.setMediaUrl(mpMaterialService.downloadMaterialUrl(message.getAccountId(),
                    message.getMediaId(), MpUtils.getMediaFileType(message.getType())));
        }
        if (StrUtil.isNotEmpty(message.getThumbMediaId())) {
            message.setThumbMediaUrl(mpMaterialService.downloadMaterialUrl(message.getAccountId(),
                    message.getThumbMediaId(), WxConsts.MediaFileType.THUMB));
        }
    }

}
