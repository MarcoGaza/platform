package cn.econets.blossom.module.mp.controller.admin.open;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.mp.controller.admin.open.vo.MpOpenCheckSignatureReqVO;
import cn.econets.blossom.module.mp.controller.admin.open.vo.MpOpenHandleMessageReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@Tag(name = "Management Backend - Public account callback")
@RestController
@RequestMapping("/mp/open")
@Validated
@Slf4j
public class MpOpenController {

    @Resource
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpAccountService mpAccountService;

    /**
     * Receive the verification signature of the WeChat public account
     *
     * Corresponding <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">Document</a>
     */
    @Operation(summary = "Verify signature") // See
    @GetMapping(value = "/{appId}", produces = "text/plain;charset=utf-8")
    public String checkSignature(@PathVariable("appId") String appId,
                                 MpOpenCheckSignatureReqVO reqVO) {
        log.info("[checkSignature][appId({}) Received authentication message from WeChat server({})]", appId, reqVO);
        // Verify request signature
        WxMpService wxMpService = mpServiceFactory.getRequiredMpService(appId);
        // Verification passed
        if (wxMpService.checkSignature(reqVO.getTimestamp(), reqVO.getNonce(), reqVO.getSignature())) {
            return reqVO.getEchostr();
        }
        // Verification failed
        return "Illegal request";
    }

    /**
     * Receive push notifications from WeChat public accounts
     *
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html">Document</a>
     */
    @Operation(summary = "Processing message")
    @PostMapping(value = "/{appId}", produces = "application/xml; charset=UTF-8")
    @OperateLog(enable = false) // Callback address，No need to record operation logs
    public String handleMessage(@PathVariable("appId") String appId,
                                @RequestBody String content,
                                MpOpenHandleMessageReqVO reqVO) {
        log.info("[handleMessage][appId({}) Push message，Parameters({}) Content({})]", appId, reqVO, content);

        // Processing appId + Multi-tenant context
        MpAccountDO account = mpAccountService.getAccountFromCache(appId);
        Assert.notNull(account, "Public Account appId({}) Does not exist", appId);
        try {
            MpContextHolder.setAppId(appId);
            return TenantUtils.execute(account.getTenantId(),
                    () -> handleMessage0(appId, content, reqVO));
        } finally {
            MpContextHolder.clear();
        }
    }

    private String handleMessage0(String appId, String content, MpOpenHandleMessageReqVO reqVO) {
        // Verify request signature
        WxMpService mppService = mpServiceFactory.getRequiredMpService(appId);
        Assert.isTrue(mppService.checkSignature(reqVO.getTimestamp(), reqVO.getNonce(), reqVO.getSignature()),
                "Illegal request");

        // First step，Parsing message
        WxMpXmlMessage inMessage = null;
        if (StrUtil.isBlank(reqVO.getEncrypt_type())) { // Plain text mode
            inMessage = WxMpXmlMessage.fromXml(content);
        } else if (Objects.equals(reqVO.getEncrypt_type(), MpOpenHandleMessageReqVO.ENCRYPT_TYPE_AES)) { // AES Encryption mode
            inMessage = WxMpXmlMessage.fromEncryptedXml(content, mppService.getWxMpConfigStorage(),
                    reqVO.getTimestamp(), reqVO.getNonce(), reqVO.getMsg_signature());
        }
        Assert.notNull(inMessage, "Message parsing failed，Reason：The message is empty");

        // Step 2，Processing messages
        WxMpMessageRouter mpMessageRouter = mpServiceFactory.getRequiredMpMessageRouter(appId);
        WxMpXmlOutMessage outMessage = mpMessageRouter.route(inMessage);
        if (outMessage == null) {
            return "";
        }

        // Step 3，Return message
        if (StrUtil.isBlank(reqVO.getEncrypt_type())) { // Plain text mode
            return outMessage.toXml();
        } else if (Objects.equals(reqVO.getEncrypt_type(), MpOpenHandleMessageReqVO.ENCRYPT_TYPE_AES)) { // AES Encryption mode
            return outMessage.toEncryptedXml(mppService.getWxMpConfigStorage());
        }
        return "";
    }

}
