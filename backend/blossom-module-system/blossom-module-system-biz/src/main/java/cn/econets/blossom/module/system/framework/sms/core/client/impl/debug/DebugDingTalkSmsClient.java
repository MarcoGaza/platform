package cn.econets.blossom.module.system.framework.sms.core.client.impl.debug;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.util.collection.MapUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.AbstractSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Based on DingTalk WebHook The SMS client implementation class for debugging
 *
 * Considering saving money，We use DingTalk WebHook Simulate sending SMS，Easy to debug。
 *
 */
public class DebugDingTalkSmsClient extends AbstractSmsClient {

    public DebugDingTalkSmsClient(SmsChannelProperties properties) {
        super(properties);
        Assert.notEmpty(properties.getApiKey(), "apiKey Cannot be empty");
        Assert.notEmpty(properties.getApiSecret(), "apiSecret Cannot be empty");
    }

    @Override
    protected void doInit() {
    }

    @Override
    public SmsSendRespDTO sendSms(Long sendLogId, String mobile,
                                  String apiTemplateId, List<KeyValue<String, Object>> templateParams) throws Throwable {
        // Build request
        String url = buildUrl("robot/send");
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", "text");
        String content = String.format("【Simulated SMS】\nMobile phone number：%s\nSMS log number：%d\nTemplate parameters：%s",
                mobile, sendLogId, MapUtils.convertMap(templateParams));
        params.put("text", MapUtil.builder().put("content", content).build());
        // Execute request
        String responseText = HttpUtil.post(url, JsonUtils.toJsonString(params));
        // Analysis results
        Map<?, ?> responseObj = JsonUtils.parseObject(responseText, Map.class);
        String errorCode = MapUtil.getStr(responseObj, "errcode");

        SmsSendRespDTO smsSendRespDTO = new SmsSendRespDTO();
        smsSendRespDTO.setSuccess(Objects.equals(errorCode, "0"));
        smsSendRespDTO.setSerialNo(StrUtil.uuid());
        smsSendRespDTO.setApiCode(errorCode);
        smsSendRespDTO.setApiMsg(MapUtil.getStr(responseObj, "errorMsg"));
        return smsSendRespDTO;
    }

    /**
     * Build request address
     *
     * See <a href="https://developers.dingtalk.com/document/app/custom-robot-access/title-nfv-794-g71">Document</a>
     *
     * @param path Request path
     * @return Request address
     */
    @SuppressWarnings("SameParameterValue")
    private String buildUrl(String path) {
        // Generate timestamp
        long timestamp = System.currentTimeMillis();
        // Generate sign
        String secret = properties.getApiSecret();
        String stringToSign = timestamp + "\n" + secret;
        byte[] signData = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, StrUtil.bytes(secret)).digest(stringToSign);
        String sign = Base64.encode(signData);
        // Build final URL
        return String.format("https://oapi.dingtalk.com/%s?access_token=%s&timestamp=%d&sign=%s",
                path, properties.getApiKey(), timestamp, sign);
    }

    @Override
    public List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) {
        throw new UnsupportedOperationException("Simulated SMS client，No need to parse callback for now");
    }

    @Override
    public SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) {
        SmsTemplateRespDTO smsTemplateRespDTO = new SmsTemplateRespDTO();
        smsTemplateRespDTO.setId(apiTemplateId);
        smsTemplateRespDTO.setContent("");
        smsTemplateRespDTO.setAuditStatus(SmsTemplateAuditStatusEnum.SUCCESS.getStatus());
        smsTemplateRespDTO.setAuditReason("");
        return smsTemplateRespDTO;
    }

}
