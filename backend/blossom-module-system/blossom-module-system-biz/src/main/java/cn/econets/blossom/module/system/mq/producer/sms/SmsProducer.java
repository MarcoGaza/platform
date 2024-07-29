package cn.econets.blossom.module.system.mq.producer.sms;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.module.system.mq.message.sms.SmsSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sms SMS related messages Producer
 *
 */
@Slf4j
@Component
public class SmsProducer {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * Send {@link SmsSendMessage} Message
     *
     * @param logId SMS log number
     * @param mobile Mobile phone number
     * @param channelId Channel Number
     * @param apiTemplateId SMS template number
     * @param templateParams SMS template parameters
     */
    public void sendSmsSendMessage(Long logId, String mobile,
                                   Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SmsSendMessage message = new SmsSendMessage();
        message.setLogId(logId);
        message.setMobile(mobile);
        message.setChannelId(channelId);
        message.setApiTemplateId(apiTemplateId);
        message.setTemplateParams(templateParams);
        applicationContext.publishEvent(message);
    }

}
