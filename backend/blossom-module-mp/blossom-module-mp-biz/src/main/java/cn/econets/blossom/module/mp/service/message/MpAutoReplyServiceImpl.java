package cn.econets.blossom.module.mp.service.message;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.exception.ErrorCode;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyUpdateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.econets.blossom.module.mp.convert.message.MpAutoReplyConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpAutoReplyDO;
import cn.econets.blossom.module.mp.dal.mysql.message.MpAutoReplyMapper;
import cn.econets.blossom.module.mp.enums.message.MpAutoReplyTypeEnum;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import cn.econets.blossom.module.mp.service.message.bo.MpMessageSendOutReqBO;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.*;

/**
 * Automatic reply of public account Service Implementation class
 *
 *
 */
@Service
@Validated
public class MpAutoReplyServiceImpl implements MpAutoReplyService {

    @Resource
    private MpMessageService mpMessageService;
    @Resource
    @Lazy // Delayed loading，Avoid circular dependencies
    private MpAccountService mpAccountService;

    @Resource
    private Validator validator;

    @Resource
    private MpAutoReplyMapper mpAutoReplyMapper;

    @Override
    public PageResult<MpAutoReplyDO> getAutoReplyPage(MpMessagePageReqVO pageVO) {
        return mpAutoReplyMapper.selectPage(pageVO);
    }

    @Override
    public MpAutoReplyDO getAutoReply(Long id) {
        return mpAutoReplyMapper.selectById(id);
    }

    @Override
    public Long createAutoReply(MpAutoReplyCreateReqVO createReqVO) {
        // First step，Verify data
        if (createReqVO.getResponseMessageType() != null) {
            MpUtils.validateMessage(validator, createReqVO.getResponseMessageType(), createReqVO);
        }
        validateAutoReplyConflict(null, createReqVO.getAccountId(), createReqVO.getType(),
                createReqVO.getRequestKeyword(), createReqVO.getRequestMessageType());

        // Step 2，Insert data
        MpAccountDO account = mpAccountService.getRequiredAccount(createReqVO.getAccountId());
        MpAutoReplyDO autoReply = MpAutoReplyConvert.INSTANCE.convert(createReqVO)
                .setAppId(account.getAppId());
        mpAutoReplyMapper.insert(autoReply);
        return autoReply.getId();
    }

    @Override
    public void updateAutoReply(MpAutoReplyUpdateReqVO updateReqVO) {
        // First step，Verify data
        if (updateReqVO.getResponseMessageType() != null) {
            MpUtils.validateMessage(validator, updateReqVO.getResponseMessageType(), updateReqVO);
        }
        MpAutoReplyDO autoReply = validateAutoReplyExists(updateReqVO.getId());
        validateAutoReplyConflict(updateReqVO.getId(), autoReply.getAccountId(), updateReqVO.getType(),
                updateReqVO.getRequestKeyword(), updateReqVO.getRequestMessageType());

        // Step 2，Update data
        MpAutoReplyDO updateObj = MpAutoReplyConvert.INSTANCE.convert(updateReqVO)
                .setAccountId(null).setAppId(null); // Avoid front-end transfer，Updating two fields
        mpAutoReplyMapper.updateById(updateObj);
    }

    /**
     * Check whether the automatic reply conflicts
     *
     * Different type，There will be different logic：
     * 1. type = SUBSCRIBE Time，No other automatic replies are allowed
     * 2. type = MESSAGE Time，Verification requestMessageType Automatic reply already exists
     * 3. type = KEYWORD Time，Verification keyword Automatic reply already exists
     *
     * @param id Automatic reply number
     * @param accountId The public account number
     * @param type Type
     * @param requestKeyword Request keywords
     * @param requestMessageType Request message type
     */
    private void validateAutoReplyConflict(Long id, Long accountId, Integer type,
                                           String requestKeyword, String requestMessageType) {
        // Get the existing automatic reply
        MpAutoReplyDO autoReply = null;
        ErrorCode errorCode = null;
        if (MpAutoReplyTypeEnum.SUBSCRIBE.getType().equals(type)) {
            autoReply = mpAutoReplyMapper.selectByAccountIdAndSubscribe(accountId);
            errorCode = AUTO_REPLY_ADD_SUBSCRIBE_FAIL_EXISTS;
        } else if (MpAutoReplyTypeEnum.MESSAGE.getType().equals(type)) {
            autoReply = mpAutoReplyMapper.selectByAccountIdAndMessage(accountId, requestMessageType);
            errorCode = AUTO_REPLY_ADD_MESSAGE_FAIL_EXISTS;
        } else if (MpAutoReplyTypeEnum.KEYWORD.getType().equals(type)) {
            autoReply = mpAutoReplyMapper.selectByAccountIdAndKeyword(accountId, requestKeyword);
            errorCode = AUTO_REPLY_ADD_KEYWORD_FAIL_EXISTS;
        }
        if (autoReply == null) {
            return;
        }

        // Conflict exists，Throws business exception
        if (id == null // Situation 1，Newly added（id == null），Record exists，Describe the conflict
            || ObjUtil.notEqual(id, autoReply.getId())) { // Case 2，Modify（id != null），id No match，Description conflict
            throw exception(errorCode);
        }
    }

    @Override
    public void deleteAutoReply(Long id) {
        // Verify fans exist
        validateAutoReplyExists(id);

        // Delete automatic reply
        mpAutoReplyMapper.deleteById(id);
    }

    private MpAutoReplyDO validateAutoReplyExists(Long id) {
        MpAutoReplyDO autoReply = mpAutoReplyMapper.selectById(id);
        if (autoReply == null) {
            throw exception(AUTO_REPLY_NOT_EXISTS);
        }
        return autoReply;
    }

    @Override
    public WxMpXmlOutMessage replyForMessage(String appId, WxMpXmlMessage wxMessage) {
        // First step，Match automatic reply
        List<MpAutoReplyDO> replies = null;
        // 1.1 Keywords
        if (wxMessage.getMsgType().equals(WxConsts.XmlMsgType.TEXT)) {
            // Exact match
            replies = mpAutoReplyMapper.selectListByAppIdAndKeywordAll(appId, wxMessage.getContent());
            if (CollUtil.isEmpty(replies)) {
                // Fuzzy matching
                replies = mpAutoReplyMapper.selectListByAppIdAndKeywordLike(appId, wxMessage.getContent());
            }
        }
        // 1.2 Message type
        if (CollUtil.isEmpty(replies)) {
            replies = mpAutoReplyMapper.selectListByAppIdAndMessage(appId, wxMessage.getMsgType());
        }
        if (CollUtil.isEmpty(replies)) {
            return null;
        }
        MpAutoReplyDO reply = CollUtil.getFirst(replies);

        // Step 2，Based on automatic reply，Create message
        MpMessageSendOutReqBO sendReqBO = MpAutoReplyConvert.INSTANCE.convert(wxMessage.getFromUser(), reply);
        return mpMessageService.sendOutMessage(sendReqBO);
    }

    @Override
    public WxMpXmlOutMessage replyForSubscribe(String appId, WxMpXmlMessage wxMessage) {
        // First step，Match automatic reply
        List<MpAutoReplyDO> replies = mpAutoReplyMapper.selectListByAppIdAndSubscribe(appId);
        MpAutoReplyDO reply = CollUtil.isNotEmpty(replies) ? CollUtil.getFirst(replies)
                : buildDefaultSubscribeAutoReply(appId); // If it does not exist，Provide a default last shift

        // Step 2，Based on automatic reply，Create message
        MpMessageSendOutReqBO sendReqBO = MpAutoReplyConvert.INSTANCE.convert(wxMessage.getFromUser(), reply);
        return mpMessageService.sendOutMessage(sendReqBO);
    }

    private MpAutoReplyDO buildDefaultSubscribeAutoReply(String appId) {
        MpAccountDO account = mpAccountService.getAccountFromCache(appId);
        Assert.notNull(account, "Public Account({}) Does not exist", appId);
        // Build default【Follow】Automatic reply
        return new MpAutoReplyDO().setAppId(appId).setAccountId(account.getId())
                .setType(MpAutoReplyTypeEnum.SUBSCRIBE.getType())
                .setResponseMessageType(WxConsts.XmlMsgType.TEXT).setResponseContent("Thanks for your attention");
    }

}
