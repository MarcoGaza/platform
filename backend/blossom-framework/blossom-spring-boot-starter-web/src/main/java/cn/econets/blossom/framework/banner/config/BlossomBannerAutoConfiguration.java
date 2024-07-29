package cn.econets.blossom.framework.banner.config;

import cn.econets.blossom.framework.banner.core.BannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Banner Automatic configuration class
 *
 */
@AutoConfiguration
public class BlossomBannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
