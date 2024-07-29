package cn.econets.blossom.framework.file.config;

import cn.econets.blossom.framework.file.core.client.FileClientFactory;
import cn.econets.blossom.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * File configuration class
 *
 */
@AutoConfiguration
public class FileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
