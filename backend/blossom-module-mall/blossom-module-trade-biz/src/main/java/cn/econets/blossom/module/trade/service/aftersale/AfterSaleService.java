package cn.econets.blossom.module.trade.service.aftersale;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSaleDisagreeReqVO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSalePageReqVO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSaleRefuseReqVO;
import cn.econets.blossom.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.aftersale.vo.AppAfterSaleDeliveryReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleDO;

/**
 * After-sales order Service Interface
 *
 */
public interface AfterSaleService {

    /**
     * 【Administrator】Get after-sales order paging
     *
     * @param pageReqVO Paged query
     * @return After-sales order paging
     */
    PageResult<AfterSaleDO> getAfterSalePage(AfterSalePageReqVO pageReqVO);

    /**
     * 【Member】Get after-sales order paging
     *
     * @param userId    User Number
     * @param pageParam Paging parameters
     * @return After-sales order paging
     */
    PageResult<AfterSaleDO> getAfterSalePage(Long userId, PageParam pageParam);

    /**
     * 【Member】Get after-sales order
     *
     * @param userId User Number
     * @param id     After-sales number
     * @return After-sales order
     */
    AfterSaleDO getAfterSale(Long userId, Long id);

    /**
     * 【Administrator】Get after-sales order
     *
     * @param id After-sales number
     * @return After-sales order
     */
    AfterSaleDO getAfterSale(Long id);

    /**
     * 【Member】Create after-sales order
     *
     * @param userId      Member User Number
     * @param createReqVO Create Request Information
     * @return After-sales number
     */
    Long createAfterSale(Long userId, AppAfterSaleCreateReqVO createReqVO);

    /**
     * 【Administrator】Agree to after-sales order
     *
     * @param userId Administrator user number
     * @param id     After-sales number
     */
    void agreeAfterSale(Long userId, Long id);

    /**
     * 【Administrator】Reject after-sales order
     *
     * @param userId     Administrator user number
     * @param auditReqVO Approval Request Information
     */
    void disagreeAfterSale(Long userId, AfterSaleDisagreeReqVO auditReqVO);

    /**
     * 【Member】Return goods
     *
     * @param userId        Member User Number
     * @param deliveryReqVO Return Request Information
     */
    void deliveryAfterSale(Long userId, AppAfterSaleDeliveryReqVO deliveryReqVO);

    /**
     * 【Administrator】Confirm receipt
     *
     * @param userId Administrator Number
     * @param id     After-sales number
     */
    void receiveAfterSale(Long userId, Long id);

    /**
     * 【Administrator】Refuse to accept the goods
     *
     * @param userId      Administrator user number
     * @param refuseReqVO Refuse to accept the goods Request Information
     */
    void refuseAfterSale(Long userId, AfterSaleRefuseReqVO refuseReqVO);

    /**
     * 【Administrator】Confirm refund
     *
     * @param userId Administrator user number
     * @param userIp Administrator User IP
     * @param id     After-sales number
     */
    void refundAfterSale(Long userId, String userIp, Long id);

    /**
     * 【Member】Cancel after-sales service
     *
     * @param userId Member User Number
     * @param id     After-sales number
     */
    void cancelAfterSale(Long userId, Long id);

    /**
     * 【Member】Get the number of ongoing after-sales orders
     *
     * @param userId User Number
     * @return Quantity
     */
    Long getApplyingAfterSaleCount(Long userId);

}
