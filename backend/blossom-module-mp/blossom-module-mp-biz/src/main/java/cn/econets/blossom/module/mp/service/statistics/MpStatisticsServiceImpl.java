package cn.econets.blossom.module.mp.service.statistics;

import cn.hutool.core.date.DateUtil;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeInterfaceResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeMsgResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.*;

/**
 * Public Account Statistics Service Implementation class
 *
 *
 */
@Service
public class MpStatisticsServiceImpl implements MpStatisticsService {

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpServiceFactory mpServiceFactory;

    @Override
    public List<WxDataCubeUserSummary> getUserSummary(Long accountId, LocalDateTime[] date) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            return mpService.getDataCubeService().getUserSummary(
                    DateUtil.date(date[0]), DateUtil.date(date[1]));
        } catch (WxErrorException e) {
            throw exception(STATISTICS_GET_USER_SUMMARY_FAIL, e.getError().getErrorMsg());
        }
    }

    @Override
    public List<WxDataCubeUserCumulate> getUserCumulate(Long accountId, LocalDateTime[] date) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            return mpService.getDataCubeService().getUserCumulate(
                    DateUtil.date(date[0]), DateUtil.date(date[1]));
        } catch (WxErrorException e) {
            throw exception(STATISTICS_GET_USER_CUMULATE_FAIL, e.getError().getErrorMsg());
        }
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMessage(Long accountId, LocalDateTime[] date) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            return mpService.getDataCubeService().getUpstreamMsg(
                    DateUtil.date(date[0]), DateUtil.date(date[1]));
        } catch (WxErrorException e) {
            throw exception(STATISTICS_GET_UPSTREAM_MESSAGE_FAIL, e.getError().getErrorMsg());
        }
    }

    @Override
    public List<WxDataCubeInterfaceResult> getInterfaceSummary(Long accountId, LocalDateTime[] date) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            return mpService.getDataCubeService().getInterfaceSummary(
                    DateUtil.date(date[0]), DateUtil.date(date[1]));
        } catch (WxErrorException e) {
            throw exception(STATISTICS_GET_INTERFACE_SUMMARY_FAIL, e.getError().getErrorMsg());
        }
    }

}
