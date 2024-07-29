package cn.econets.blossom.module.statistics.job.product;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.statistics.service.product.ProductStatisticsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// TODO Missing one Job Configuration；Equal and Product Configure together

/**
 * Product Statistics Job
 *
 */
@Component
public class ProductStatisticsJob implements JobHandler {

    @Resource
    private ProductStatisticsService productStatisticsService;

    /**
     * Execute commodity statistics tasks
     *
     * @param param Number of days to be counted，Can only be positive integers，1 Represents yesterday's data
     * @return Statistical results
     */
    @Override
    @TenantJob
    public String execute(String param) {
        // Default yesterday
        param = ObjUtil.defaultIfBlank(param, "1");
        // Check the rationality of parameters
        if (!NumberUtil.isInteger(param)) {
            throw new RuntimeException("The parameters of the commodity statistics task can only be positive integers");
        }
        Integer days = Convert.toInt(param, 0);
        if (days < 1) {
            throw new RuntimeException("Parameters of commodity statistics tasks can only be positive integers");
        }
        String result = productStatisticsService.statisticsProduct(days);
        return StrUtil.format("Product Statistics:\n{}", result);
    }

}
