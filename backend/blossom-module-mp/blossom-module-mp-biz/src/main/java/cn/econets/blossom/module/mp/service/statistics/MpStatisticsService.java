package cn.econets.blossom.module.mp.service.statistics;

import me.chanjar.weixin.mp.bean.datacube.WxDataCubeInterfaceResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeMsgResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Public Account Statistics Service Interface
 *
 *
 */
public interface MpStatisticsService {

    /**
     * Get the data of fans' increase and decrease
     *
     * @param accountId Public account number
     * @param date Time interval
     * @return Fans increase and decrease data
     */
    List<WxDataCubeUserSummary> getUserSummary(Long accountId, LocalDateTime[] date);

    /**
     * Get fans' cumulative data
     *
     * @param accountId Public account number
     * @param date Time interval
     * @return Accumulated fan data
     */
    List<WxDataCubeUserCumulate> getUserCumulate(Long accountId, LocalDateTime[] date);

    /**
     * Get message sending profile data
     *
     * @param accountId Public account number
     * @param date Time interval
     * @return Message sending profile data
     */
    List<WxDataCubeMsgResult> getUpstreamMessage(Long accountId, LocalDateTime[] date);

    /**
     * Get interface analysis data
     *
     * @param accountId Public account number
     * @param date Time interval
     * @return Interface analysis data
     */
    List<WxDataCubeInterfaceResult> getInterfaceSummary(Long accountId, LocalDateTime[] date);

}
