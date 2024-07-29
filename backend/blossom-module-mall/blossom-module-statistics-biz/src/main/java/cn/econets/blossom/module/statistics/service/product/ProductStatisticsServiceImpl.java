package cn.econets.blossom.module.statistics.service.product;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.pojo.SortablePageParam;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsReqVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.product.ProductStatisticsDO;
import cn.econets.blossom.module.statistics.dal.mysql.product.ProductStatisticsMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Product Statistics Service Implementation class
 *
 */
@Service
@Validated
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

    @Resource
    private ProductStatisticsMapper productStatisticsMapper;


    @Override
    public PageResult<ProductStatisticsDO> getProductStatisticsRankPage(ProductStatisticsReqVO reqVO, SortablePageParam pageParam) {
        PageUtils.buildDefaultSortingField(pageParam, ProductStatisticsDO::getBrowseCount); // Default pageview count in reverse order
        return productStatisticsMapper.selectPageGroupBySpuId(reqVO, pageParam);
    }

    @Override
    public DataComparisonRespVO<ProductStatisticsRespVO> getProductStatisticsAnalyse(ProductStatisticsReqVO reqVO) {
        LocalDateTime beginTime = ArrayUtil.get(reqVO.getTimes(), 0);
        LocalDateTime endTime = ArrayUtil.get(reqVO.getTimes(), 1);

        // Statistics
        ProductStatisticsRespVO value = productStatisticsMapper.selectVoByTimeBetween(reqVO);
        // Comparison data
        LocalDateTime referenceBeginTime = beginTime.minus(Duration.between(beginTime, endTime));
        ProductStatisticsReqVO referenceReqVO = new ProductStatisticsReqVO(new LocalDateTime[]{referenceBeginTime, beginTime});
        ProductStatisticsRespVO reference = productStatisticsMapper.selectVoByTimeBetween(referenceReqVO);
        return new DataComparisonRespVO<>(value, reference);
    }

    @Override
    public List<ProductStatisticsDO> getProductStatisticsList(ProductStatisticsReqVO reqVO) {
        return productStatisticsMapper.selectListByTimeBetween(reqVO);
    }

    @Override
    public String statisticsProduct(Integer days) {
        LocalDateTime today = LocalDateTime.now();
        return IntStream.rangeClosed(1, days)
                .mapToObj(day -> statisticsProduct(today.minusDays(day)))
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    /**
     * Statistics of product data
     *
     * @param date Date to be counted
     * @return Statistical results
     */
    private String statisticsProduct(LocalDateTime date) {
        // 1. Processing statistics time range
        LocalDateTime beginTime = LocalDateTimeUtil.beginOfDay(date);
        LocalDateTime endTime = LocalDateTimeUtil.endOfDay(date);
        String dateStr = DatePattern.NORM_DATE_FORMATTER.format(date);
        // 2. Check whether the day has been counted
        Long count = productStatisticsMapper.selectCountByTimeBetween(beginTime, endTime);
        if (count != null && count > 0) {
            return dateStr + " Data already exists，If you need to recalculate，Please delete the corresponding data first";
        }

        StopWatch stopWatch = new StopWatch(dateStr);
        stopWatch.start();
        // 4. Paging Statistics，Avoid when there is a lot of data in the product table，Timeout problem occurred
        final int pageSize = 100;
        for (int pageNo = 1; ; pageNo++) {
            IPage<ProductStatisticsDO> page = productStatisticsMapper.selectStatisticsResultPageByTimeBetween(
                    Page.of(pageNo, pageSize, false), beginTime, endTime);
            if (CollUtil.isEmpty(page.getRecords())) {
                break;
            }
            // 4.1 Calculate visitor payment conversion rate（Percentage）
            for (ProductStatisticsDO record : page.getRecords()) {
                record.setTime(date.toLocalDate());
                if (record.getBrowseUserCount() != null && ObjUtil.notEqual(record.getBrowseUserCount(), 0)) {
                    record.setBrowseConvertPercent(100 * record.getOrderPayCount() / record.getBrowseUserCount());
                }
            }
            // 4.2 Insert data
            productStatisticsMapper.insertBatch(page.getRecords());
        }
        return stopWatch.prettyPrint();
    }

}