package cn.econets.blossom.module.trade.service.delivery;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreCreateReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStorePageReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreUpdateReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Self-pickup store Service Interface
 *
 */
public interface DeliveryPickUpStoreService {

    /**
     * Create a self-pickup store
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDeliveryPickUpStore(@Valid DeliveryPickUpStoreCreateReqVO createReqVO);

    /**
     * Update self-pickup stores
     *
     * @param updateReqVO Update information
     */
    void updateDeliveryPickUpStore(@Valid DeliveryPickUpStoreUpdateReqVO updateReqVO);

    /**
     * Delete the self-pickup store
     *
     * @param id Number
     */
    void deleteDeliveryPickUpStore(Long id);

    /**
     * Get the self-pickup store
     *
     * @param id Number
     * @return Self-pickup store
     */
    DeliveryPickUpStoreDO getDeliveryPickUpStore(Long id);

    /**
     * Get the list of self-pickup stores
     *
     * @param ids Number
     * @return Self-pickup store list
     */
    List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreList(Collection<Long> ids);

    /**
     * Get the self-pickup store page
     *
     * @param pageReqVO Paged query
     * @return Self-pickup store page
     */
    PageResult<DeliveryPickUpStoreDO> getDeliveryPickUpStorePage(DeliveryPickUpStorePageReqVO pageReqVO);

    /**
     * Get the list of self-pickup stores in the specified status
     *
     * @param status Status
     * @return Self-pickup store list
     */
    List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreListByStatus(Integer status);
}
