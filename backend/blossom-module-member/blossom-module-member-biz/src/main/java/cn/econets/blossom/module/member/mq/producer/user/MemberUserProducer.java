package cn.econets.blossom.module.member.mq.producer.user;

import cn.econets.blossom.module.member.message.user.MemberUserCreateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Member User Producer
 *
 * 
 */
@Slf4j
@Component
public class MemberUserProducer {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * Send {@link MemberUserCreateMessage} Message
     *
     * @param userId User Number
     */
    public void sendUserCreateMessage(Long userId) {
        applicationContext.publishEvent(new MemberUserCreateMessage().setUserId(userId));
    }

}
