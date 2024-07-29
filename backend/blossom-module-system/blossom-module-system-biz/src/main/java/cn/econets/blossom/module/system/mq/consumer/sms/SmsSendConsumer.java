package cn.econets.blossom.module.system.mq.consumer.sms;

import cn.econets.blossom.module.system.mq.message.sms.SmsSendMessage;
import cn.econets.blossom.module.system.service.sms.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Targeting {@link SmsSendMessage} Consumers
 *
 */
@Component
@Slf4j
public class SmsSendConsumer {

    @Resource
    private SmsSendService smsSendService;

    @EventListener
    @Async // Spring Event Default is Producer Sent thread，Passed @Async Implementing Asynchronous Mode
    public void onMessage(SmsSendMessage message) {
        log.info("[onMessage][Message content({})]", message);
        smsSendService.doSendSms(message);
    }

}
