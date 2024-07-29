package cn.econets.blossom.module.system.framework.sms.core.client.impl.aliyun;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.collection.MapUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.AbstractSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.hutool.core.lang.Assert;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsTemplateRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsTemplateResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Ali SMS client implementation class
 *
 */
@Slf4j
public class AliyunSmsClient extends AbstractSmsClient {

    /**
     * Call successful code
     */
    public static final String API_CODE_SUCCESS = "OK";

    /**
     * REGION, Use Hangzhou
     */
    private static final String ENDPOINT = "cn-hangzhou";

    /**
     * Alibaba Cloud Client
     */
    private volatile IAcsClient client;

    public AliyunSmsClient(SmsChannelProperties properties) {
        super(properties);
        Assert.notEmpty(properties.getApiKey(), "apiKey Cannot be empty");
        Assert.notEmpty(properties.getApiSecret(), "apiSecret Cannot be empty");
    }

    @Override
    protected void doInit() {
        IClientProfile profile = DefaultProfile.getProfile(ENDPOINT, properties.getApiKey(), properties.getApiSecret());
        client = new DefaultAcsClient(profile);
    }

    @Override
    public SmsSendRespDTO sendSms(Long sendLogId, String mobile, String apiTemplateId,
                                  List<KeyValue<String, Object>> templateParams) throws Throwable {
        // Build request
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(mobile);
        request.setSignName(properties.getSignature());
        request.setTemplateCode(apiTemplateId);
        request.setTemplateParam(JsonUtils.toJsonString(MapUtils.convertMap(templateParams)));
        request.setOutId(String.valueOf(sendLogId));
        // Execute request
        SendSmsResponse response = client.getAcsResponse(request);
        SmsSendRespDTO smsSendRespDTO = new SmsSendRespDTO();
        smsSendRespDTO.setSuccess(Objects.equals(response.getCode(), API_CODE_SUCCESS));
        smsSendRespDTO.setSerialNo(response.getBizId());
        smsSendRespDTO.setApiRequestId(response.getRequestId());
        smsSendRespDTO.setApiCode(response.getCode());
        smsSendRespDTO.setApiMsg(response.getMessage());
        return smsSendRespDTO;
    }

    @Override
    public List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) {
        List<SmsReceiveStatus> statuses = JsonUtils.parseArray(text, SmsReceiveStatus.class);
        return CollectionUtils.convertList(statuses, status -> {
            SmsReceiveRespDTO smsReceiveRespDTO = new SmsReceiveRespDTO();
            smsReceiveRespDTO.setSuccess(status.getSuccess());
            smsReceiveRespDTO.setErrorCode(status.getErrCode());
            smsReceiveRespDTO.setErrorMsg(status.getErrMsg());
            smsReceiveRespDTO.setMobile(status.getPhoneNumber());
            smsReceiveRespDTO.setReceiveTime(status.getReportTime());
            smsReceiveRespDTO.setSerialNo(status.getBizId());
            smsReceiveRespDTO.setLogId(Long.valueOf(status.getOutId()));
            return smsReceiveRespDTO;
        });
    }

    @Override
    public SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) throws Throwable {
        // Build request
        QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
        request.setTemplateCode(apiTemplateId);
        // Execute request
        QuerySmsTemplateResponse response = client.getAcsResponse(request);
        if (response.getTemplateStatus() == null) {
            return null;
        }
        SmsTemplateRespDTO smsTemplateRespDTO = new SmsTemplateRespDTO();
        smsTemplateRespDTO.setId(response.getTemplateCode());
        smsTemplateRespDTO.setContent(response.getTemplateContent());
        smsTemplateRespDTO.setAuditStatus(convertSmsTemplateAuditStatus(response.getTemplateStatus()));
        smsTemplateRespDTO.setAuditReason(response.getReason());
        return smsTemplateRespDTO;
    }

    @VisibleForTesting
    Integer convertSmsTemplateAuditStatus(Integer templateStatus) {
        switch (templateStatus) {
            case 0: return SmsTemplateAuditStatusEnum.CHECKING.getStatus();
            case 1: return SmsTemplateAuditStatusEnum.SUCCESS.getStatus();
            case 2: return SmsTemplateAuditStatusEnum.FAIL.getStatus();
            default: throw new IllegalArgumentException(String.format("Unknown review status(%d)", templateStatus));
        }
    }

    /**
     * SMS receiving status
     *
     * See <a href="https://help.aliyun.com/document_detail/101867.html">Document</a>
     *
     */
    @Data
    public static class SmsReceiveStatus {

        /**
         * Mobile phone number
         */
        @JsonProperty("phone_number")
        private String phoneNumber;
        /**
         * Send time
         */
        @JsonProperty("send_time")
        @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = DateUtils.TIME_ZONE_DEFAULT)
        private LocalDateTime sendTime;
        /**
         * Status report time
         */
        @JsonProperty("report_time")
        @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = DateUtils.TIME_ZONE_DEFAULT)
        private LocalDateTime reportTime;
        /**
         * Whether the reception was successful
         */
        private Boolean success;
        /**
         * Status report description
         */
        @JsonProperty("err_msg")
        private String errMsg;
        /**
         * Status report code
         */
        @JsonProperty("err_code")
        private String errCode;
        /**
         * Send sequence number
         */
        @JsonProperty("biz_id")
        private String bizId;
        /**
         * User serial number
         *
         * Here we pass SysSmsLogDO Log number
         */
        @JsonProperty("out_id")
        private String outId;
        /**
         * SMS length，For example 1、2、3
         *
         * 140 Bytes count as one SMS，The message length exceeds 140 Bytes will be split into multiple text messages for sending
         */
        @JsonProperty("sms_size")
        private Integer smsSize;

    }

}
