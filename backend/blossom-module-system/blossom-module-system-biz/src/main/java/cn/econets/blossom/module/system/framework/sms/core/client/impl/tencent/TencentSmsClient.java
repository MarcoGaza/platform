package cn.econets.blossom.module.system.framework.sms.core.client.impl.tencent;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.util.collection.ArrayUtils;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.AbstractSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Tencent Cloud SMS function implementation
 *
 * See <a href="https://cloud.tencent.com/document/product/382/52077">Document</a>
 *
 */
public class TencentSmsClient extends AbstractSmsClient {

    /**
     * Call successful code
     */
    public static final String API_CODE_SUCCESS = "Ok";

    /**
     * REGION，Use Nanjing
     */
    private static final String ENDPOINT = "ap-nanjing";

    /**
     * Is it international?/SMS messages for Hong Kong, Macau and Taiwan：
     *
     * 0：Indicates domestic text messages。
     * 1：Indicates international/SMS messages for Hong Kong, Macau and Taiwan。
     */
    private static final long INTERNATIONAL_CHINA = 0L;

    private SmsClient client;

    public TencentSmsClient(SmsChannelProperties properties) {
        super(properties);
        Assert.notEmpty(properties.getApiSecret(), "apiSecret Cannot be empty");
        validateSdkAppId(properties);
    }

    @Override
    protected void doInit() {
        // Instantiate an authentication object，The Tencent Cloud account key pair needs to be passed in as input secretId，secretKey
        Credential credential = new Credential(getApiKey(), properties.getApiSecret());
        client = new SmsClient(credential, ENDPOINT);
    }

    /**
     * Parameter verification of Tencent Cloud SDK AppId
     *
     * The reason is：When Tencent Cloud sends SMS messages，Additional parameters are required sdkAppId
     *
     * Solution：Considering not to damage the original apiKey + apiSecret Structure，So will secretId Splice to apiKey In the field，The format is "secretId sdkAppId"。
     *
     * @param properties Configuration
     */
    private static void validateSdkAppId(SmsChannelProperties properties) {
        String combineKey = properties.getApiKey();
        Assert.notEmpty(combineKey, "apiKey Cannot be empty");
        String[] keys = combineKey.trim().split(" ");
        Assert.isTrue(keys.length == 2, "Tencent Cloud SMS apiKey Configuration format error，Please configure for[secretId sdkAppId]");
    }

    private String getSdkAppId() {
        return StrUtil.subAfter(properties.getApiKey(), " ", true);
    }

    private String getApiKey() {
        return StrUtil.subBefore(properties.getApiKey(), " ", true);
    }

    @Override
    public SmsSendRespDTO sendSms(Long sendLogId, String mobile,
                                  String apiTemplateId, List<KeyValue<String, Object>> templateParams) throws Throwable {
        // Build request
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppId(getSdkAppId());
        request.setPhoneNumberSet(new String[]{mobile});
        request.setSignName(properties.getSignature());
        request.setTemplateId(apiTemplateId);
        request.setTemplateParamSet(ArrayUtils.toArray(templateParams, e -> String.valueOf(e.getValue())));
        SessionContext sessionContext = new SessionContext();
        sessionContext.setLogId(sendLogId);
        request.setSessionContext(JsonUtils.toJsonString(sessionContext));
        // Execute request
        SendSmsResponse response = client.SendSms(request);
        SendStatus status = response.getSendStatusSet()[0];

        SmsSendRespDTO smsSendRespDTO = new SmsSendRespDTO();
        smsSendRespDTO.setSuccess(Objects.equals(status.getCode(), API_CODE_SUCCESS));
        smsSendRespDTO.setSerialNo(status.getSerialNo());
        smsSendRespDTO.setApiRequestId(response.getRequestId());
        smsSendRespDTO.setApiCode(status.getCode());
        smsSendRespDTO.setApiMsg(status.getMessage());
        return smsSendRespDTO;
    }

    @Override
    public List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) {
        List<SmsReceiveStatus> callback = JsonUtils.parseArray(text, SmsReceiveStatus.class);
        return CollectionUtils.convertList(callback, status -> {
            SmsReceiveRespDTO smsReceiveRespDTO = new SmsReceiveRespDTO();
            smsReceiveRespDTO.setSuccess(SmsReceiveStatus.SUCCESS_CODE.equalsIgnoreCase(status.getStatus()));
            smsReceiveRespDTO.setErrorCode(status.getErrCode());
            smsReceiveRespDTO.setErrorMsg(status.getDescription());
            smsReceiveRespDTO.setMobile(status.getMobile());
            smsReceiveRespDTO.setReceiveTime(status.getReceiveTime());
            smsReceiveRespDTO.setSerialNo(status.getSerialNo());
            smsReceiveRespDTO.setLogId(status.getSessionContext().getLogId());
            return smsReceiveRespDTO;
        });
    }

    @Override
    public SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) throws Throwable {
        // Build request
        DescribeSmsTemplateListRequest request = new DescribeSmsTemplateListRequest();
        request.setTemplateIdSet(new Long[]{Long.parseLong(apiTemplateId)});
        request.setInternational(INTERNATIONAL_CHINA);
        // Execute request
        DescribeSmsTemplateListResponse response = client.DescribeSmsTemplateList(request);
        DescribeTemplateListStatus status = response.getDescribeTemplateStatusSet()[0];
        if (status == null || status.getStatusCode() == null) {
            return null;
        }

        SmsTemplateRespDTO smsTemplateRespDTO = new SmsTemplateRespDTO();
        smsTemplateRespDTO.setId(status.getTemplateId().toString());
        smsTemplateRespDTO.setContent(status.getTemplateContent());
        smsTemplateRespDTO.setAuditStatus(convertSmsTemplateAuditStatus(status.getStatusCode().intValue()));
        smsTemplateRespDTO.setAuditReason(status.getReviewReply());
        return smsTemplateRespDTO;
    }

    @VisibleForTesting
    Integer convertSmsTemplateAuditStatus(int templateStatus) {
        switch (templateStatus) {
            case 1: return SmsTemplateAuditStatusEnum.CHECKING.getStatus();
            case 0: return SmsTemplateAuditStatusEnum.SUCCESS.getStatus();
            case -1: return SmsTemplateAuditStatusEnum.FAIL.getStatus();
            default: throw new IllegalArgumentException(String.format("Unknown review status(%d)", templateStatus));
        }
    }

    @Data
    private static class SmsReceiveStatus {

        /**
         * SMS received successfully code
         */
        public static final String SUCCESS_CODE = "SUCCESS";

        /**
         * The time when the user actually received the SMS
         */
        @JsonProperty("user_receive_time")
        @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = DateUtils.TIME_ZONE_DEFAULT)
        private LocalDateTime receiveTime;

        /**
         * Country（or region）Code
         */
        @JsonProperty("nationcode")
        private String nationCode;

        /**
         * Mobile phone number
         */
        private String mobile;

        /**
         * Whether the SMS message is actually received，SUCCESS（Success）、FAIL（Failed）
         */
        @JsonProperty("report_status")
        private String status;

        /**
         * User receives SMS status code error message
         */
        @JsonProperty("errmsg")
        private String errCode;

        /**
         * User receiving SMS status description
         */
        @JsonProperty("description")
        private String description;

        /**
         * This time sending identification ID（Returned by the sending interfaceSerialNoCorresponding）
         */
        @JsonProperty("sid")
        private String serialNo;

        /**
         * User's session Content（Request parameters of the sending interface SessionContext Consistent）
         */
        @JsonProperty("ext")
        private SessionContext sessionContext;

    }

    @VisibleForTesting
    @Data
    static class SessionContext {

        /**
         * Send SMS recordsid
         */
        private Long logId;

    }

}
