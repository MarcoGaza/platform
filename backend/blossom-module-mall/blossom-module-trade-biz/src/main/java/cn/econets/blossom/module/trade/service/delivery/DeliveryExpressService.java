package cn.econets.blossom.module.trade.service.delivery;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressCreateReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressExportReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressPageReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressUpdateReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Express Delivery Company Service Interface
 *
 */
public interface DeliveryExpressService {

    /**
     * Create a courier company
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDeliveryExpress(@Valid DeliveryExpressCreateReqVO createReqVO);

    /**
     * Update courier company
     *
     * @param updateReqVO Update information
     */
    void updateDeliveryExpress(@Valid DeliveryExpressUpdateReqVO updateReqVO);

    /**
     * Delete courier company
     *
     * @param id Number
     */
    void deleteDeliveryExpress(Long id);

    /**
     * Get the courier company
     *
     * @param id Number
     * @return Express Delivery Company
     */
    DeliveryExpressDO getDeliveryExpress(Long id);

    /**
     * Check whether the courier company is legal
     *
     * @param id Number
     * @return Express Delivery Company
     */
    DeliveryExpressDO validateDeliveryExpress(Long id);

    /**
     * Get the courier company page
     *
     * @param pageReqVO Paged query
     * @return Express delivery company page
     */
    PageResult<DeliveryExpressDO> getDeliveryExpressPage(DeliveryExpressPageReqVO pageReqVO);

    /**
     * Get the list of courier companies, Used for Excel Export
     *
     * @param exportReqVO Query conditions
     * @return Express delivery company list
     */
    List<DeliveryExpressDO> getDeliveryExpressList(DeliveryExpressExportReqVO exportReqVO);

    /**
     * Get the list of express companies in the specified status
     *
     * @param status Status
     * @return Express Delivery Company List
     */
    List<DeliveryExpressDO> getDeliveryExpressListByStatus(Integer status);

}
