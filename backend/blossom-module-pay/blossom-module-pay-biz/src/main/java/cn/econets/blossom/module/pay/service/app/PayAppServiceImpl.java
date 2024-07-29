package cn.econets.blossom.module.pay.service.app;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppUpdateReqVO;
import cn.econets.blossom.module.pay.convert.app.PayAppConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.mysql.app.PayAppMapper;
import cn.econets.blossom.module.pay.enums.ErrorCodeConstants;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Payment Application Service Implementation class
 *
 *
 */
@Service
@Validated
public class PayAppServiceImpl implements PayAppService {

    @Resource
    private PayAppMapper appMapper;

    @Resource
    @Lazy // Delayed loading，Avoid circular dependency errors
    private PayOrderService orderService;
    @Resource
    @Lazy // Delayed loading，Avoid circular dependency errors
    private PayRefundService refundService;

    @Override
    public Long createApp(PayAppCreateReqVO createReqVO) {
        // Insert
        PayAppDO app = PayAppConvert.INSTANCE.convert(createReqVO);
        appMapper.insert(app);
        // Return
        return app.getId();
    }

    @Override
    public void updateApp(PayAppUpdateReqVO updateReqVO) {
        // Verify existence
        validateAppExists(updateReqVO.getId());
        // Update
        PayAppDO updateObj = PayAppConvert.INSTANCE.convert(updateReqVO);
        appMapper.updateById(updateObj);
    }

    @Override
    public void updateAppStatus(Long id, Integer status) {
        // Verify merchant existence
        validateAppExists(id);
        // Update status
        appMapper.updateById(new PayAppDO().setId(id).setStatus(status));
    }

    @Override
    public void deleteApp(Long id) {
        // Check existence
        validateAppExists(id);
        // Check whether the associated data exists
        if (orderService.getOrderCountByAppId(id) > 0) {
            throw exception(APP_EXIST_ORDER_CANT_DELETE);
        }
        if (refundService.getRefundCountByAppId(id) > 0) {
            throw exception(APP_EXIST_REFUND_CANT_DELETE);
        }

        // Delete
        appMapper.deleteById(id);
    }

    private void validateAppExists(Long id) {
        if (appMapper.selectById(id) == null) {
            throw exception(APP_NOT_FOUND);
        }
    }

    @Override
    public PayAppDO getApp(Long id) {
        return appMapper.selectById(id);
    }

    @Override
    public List<PayAppDO> getAppList(Collection<Long> ids) {
        return appMapper.selectBatchIds(ids);
    }

    @Override
    public List<PayAppDO> getAppList() {
         return appMapper.selectList();
    }

    @Override
    public PageResult<PayAppDO> getAppPage(PayAppPageReqVO pageReqVO) {
        return appMapper.selectPage(pageReqVO);
    }

    @Override
    public PayAppDO validPayApp(Long id) {
        PayAppDO app = appMapper.selectById(id);
        // Check if it exists
        if (app == null) {
            throw exception(ErrorCodeConstants.APP_NOT_FOUND);
        }
        // Check whether to disable
        if (CommonStatusEnum.DISABLE.getStatus().equals(app.getStatus())) {
            throw exception(ErrorCodeConstants.APP_IS_DISABLE);
        }
        return app;
    }

}
