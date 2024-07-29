package cn.econets.blossom.module.pay.service.app;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.app.vo.PayAppUpdateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Payment Application Service Interface
 *
 *
 */
public interface PayAppService {

    /**
     * Create a payment application
     *
     * @param createReqVO Create
     * @return Number
     */
    Long createApp(@Valid PayAppCreateReqVO createReqVO);

    /**
     * Update payment application
     *
     * @param updateReqVO Update
     */
    void updateApp(@Valid PayAppUpdateReqVO updateReqVO);

    /**
     * Modify application status
     *
     * @param id     Application Number
     * @param status Status
     */
    void updateAppStatus(Long id, Integer status);

    /**
     * Delete payment application
     *
     * @param id Number
     */
    void deleteApp(Long id);

    /**
     * Get payment application
     *
     * @param id Number
     * @return Payment Application
     */
    PayAppDO getApp(Long id);

    /**
     * Get the payment application list
     *
     * @param ids Number
     * @return Payment application list
     */
    List<PayAppDO> getAppList(Collection<Long> ids);

    /**
     * Get the payment application list
     *
     * @return Payment application list
     */
    List<PayAppDO> getAppList();

    /**
     * Get payment application paging
     *
     * @param pageReqVO Paged query
     * @return Payment application paging
     */
    PageResult<PayAppDO> getAppPage(PayAppPageReqVO pageReqVO);

    /**
     * Get the merchant with the specified number Map
     *
     * @param ids Application ID Collection
     * @return Merchant Map
     */
    default Map<Long, PayAppDO> getAppMap(Collection<Long> ids) {
        List<PayAppDO> list =  getAppList(ids);
        return CollectionUtils.convertMap(list, PayAppDO::getId);
    }

    /**
     * Legality of payment applications
     *
     * If it is illegal，Throw {@link ServiceException} Business exception
     *
     * @param id Application number
     * @return Application
     */
    PayAppDO validPayApp(Long id);

}
