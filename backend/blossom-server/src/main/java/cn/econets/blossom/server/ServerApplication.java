package cn.econets.blossom.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project startup class
 */
@SuppressWarnings("SpringComponentScan") // Ignore IDEA Unrecognizable ${application.info.base-package}
@SpringBootApplication(scanBasePackages = {"${application.info.base-package}.server", "${application.info.base-package}.module"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}
