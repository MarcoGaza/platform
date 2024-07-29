package cn.econets.blossom.module.trade.service.delivery;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.DeliveryExpressTemplateCreateReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.DeliveryExpressTemplateDetailRespVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.DeliveryExpressTemplatePageReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.DeliveryExpressTemplateUpdateReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateDO;
import cn.econets.blossom.module.trade.service.delivery.bo.DeliveryExpressTemplateRespBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Express delivery fee template Service Interface
 *
 */
public interface DeliveryExpressTemplateService {

    /**
     * Create a courier freight template
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDeliveryExpressTemplate(@Valid DeliveryExpressTemplateCreateReqVO createReqVO);

    /**
     * Update express delivery fee template
     *
     * @param updateReqVO Update information
     */
    void updateDeliveryExpressTemplate(@Valid DeliveryExpressTemplateUpdateReqVO updateReqVO);

    /**
     * Delete the express delivery fee template
     *
     * @param id Number
     */
    void deleteDeliveryExpressTemplate(Long id);

    /**
     * Get the express delivery fee template
     *
     * @param id Number
     * @return Express delivery fee template details
     */
    DeliveryExpressTemplateDetailRespVO getDeliveryExpressTemplate(Long id);

    /**
     * Get the express delivery fee template list
     *
     * @param ids Number
     * @return Express delivery fee template list
     */
    List<DeliveryExpressTemplateDO> getDeliveryExpressTemplateList(Collection<Long> ids);

    /**
     * Get the express delivery fee template list
     *
     * @return Express delivery fee template list
     */
    List<DeliveryExpressTemplateDO> getDeliveryExpressTemplateList();

    /**
     * Get the express delivery template page
     *
     * @param pageReqVO Paged query
     * @return Express delivery fee template paging
     */
    PageResult<DeliveryExpressTemplateDO> getDeliveryExpressTemplatePage(DeliveryExpressTemplatePageReqVO pageReqVO);

    /**
     * Verify express freight template
     *
     * If verification fails，Throw {@link cn.econets.blossom.framework.common.exception.ServiceException} Abnormal
     *
     * @param templateId Template number
     * @return Express delivery fee template
     */
    DeliveryExpressTemplateDO validateDeliveryExpressTemplate(Long templateId);

    /**
     * Based on the freight template number array and the recipient address area number，Get matching shipping template
     *
     * @param ids    Numbered list
     * @param areaId Area Number
     * @return Map (templateId -> Shipping template settings)
     */
    Map<Long, DeliveryExpressTemplateRespBO> getExpressTemplateMapByIdsAndArea(Collection<Long> ids, Integer areaId);

}
