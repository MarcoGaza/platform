package cn.econets.blossom.framework.captcha.config;


import cn.econets.blossom.framework.captcha.core.service.RedisCaptchaServiceImpl;
import com.xingyuv.captcha.properties.AjCaptchaProperties;
import com.xingyuv.captcha.service.CaptchaCacheService;
import com.xingyuv.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@AutoConfiguration
public class CaptchaConfiguration {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config) {
        // Cache type redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (ret instanceof RedisCaptchaServiceImpl) {
            ((RedisCaptchaServiceImpl) ret).setStringRedisTemplate(stringRedisTemplate);
        }
        return ret;
    }

}
