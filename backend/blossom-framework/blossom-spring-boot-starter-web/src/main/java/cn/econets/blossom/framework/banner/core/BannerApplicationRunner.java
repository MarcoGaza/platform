package cn.econets.blossom.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * After the project is successfully launched，Provide document-related addresses
 *
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Value("${server.port:58080}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // Delay 1 seconds，Ensure output to the end
            log.info("\n----------------------------------------------------------\n\t" +
                            "Project started successfully！\n\t" +
                            "Interface Documentation: \t{} \n\t" +
                            "----------------------------------------------------------",
                    "http://localhost:"+port+"/doc.html");

            // Member Center
            if (isNotPresent("cn.econets.blossom.module.member.framework.web.config.MemberWebConfiguration")) {
                System.out.println("[Member Center Module blossom-module-member - Disabled]");
            }
            // Workflow
            if (isNotPresent("cn.econets.blossom.framework.flowable.config.FlowableConfiguration")) {
                System.out.println("[Workflow Module blossom-module-bpm - Disabled]");
            }
            // WeChat public account
            if (isNotPresent("cn.econets.blossom.module.mp.framework.mp.config.MpConfiguration")) {
                System.out.println("[WeChat public account blossom-module-mp - Disabled]");
            }
            // Mall System
            if (isNotPresent("cn.econets.blossom.module.trade.framework.web.config.TradeWebConfiguration")) {
                System.out.println("[Mall System blossom-module-mall - Disabled]");
            }
            // Payment Platform
            if (isNotPresent("cn.econets.blossom.module.pay.framework.pay.config.PayConfiguration")) {
                System.out.println("[Payment system blossom-module-pay - Disabled]");
            }
            // Customer Relationship System
            if (isNotPresent("cn.econets.blossom.module.pay.framework.pay.config.PayConfiguration")) {
                System.out.println("[Customer Relationship System blossom-module-crm - Disabled]");
            }
        });
    }

    private static boolean isNotPresent(String className) {
        return !ClassUtils.isPresent(className, ClassUtils.getDefaultClassLoader());
    }

}
