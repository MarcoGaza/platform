package cn.econets.blossom.module.pay.dal.mysql.notify;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PayNotifyTaskMapper extends BaseMapperX<PayNotifyTaskDO> {

    /**
     * Get the required notification PayNotifyTaskDO Record。The following conditions must be met：
     *
     * 1. status Non-successful
     * 2. nextNotifyTime Smaller than the current time
     *
     * @return PayTransactionNotifyTaskDO Array
     */
    default List<PayNotifyTaskDO> selectListByNotify() {
        return selectList(new LambdaQueryWrapper<PayNotifyTaskDO>()
                .in(PayNotifyTaskDO::getStatus, PayNotifyStatusEnum.WAITING.getStatus(),
                        PayNotifyStatusEnum.REQUEST_SUCCESS.getStatus(), PayNotifyStatusEnum.REQUEST_FAILURE.getStatus())
                .le(PayNotifyTaskDO::getNextNotifyTime, LocalDateTime.now()));
    }

    default PageResult<PayNotifyTaskDO> selectPage(PayNotifyTaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayNotifyTaskDO>()
                .eqIfPresent(PayNotifyTaskDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayNotifyTaskDO::getType, reqVO.getType())
                .eqIfPresent(PayNotifyTaskDO::getDataId, reqVO.getDataId())
                .eqIfPresent(PayNotifyTaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PayNotifyTaskDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .betweenIfPresent(PayNotifyTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayNotifyTaskDO::getId));
    }

}
