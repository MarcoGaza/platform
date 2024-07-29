package cn.econets.blossom.module.promotion.service.diy;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplateCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplatePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplatePropertyUpdateRequestVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplateUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyTemplateDO;

import javax.validation.Valid;

/**
 * Decoration template Service Interface
 *
 */
public interface DiyTemplateService {

    /**
     * Create decoration template
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDiyTemplate(@Valid DiyTemplateCreateReqVO createReqVO);

    /**
     * Update decoration template
     *
     * @param updateReqVO Update information
     */
    void updateDiyTemplate(@Valid DiyTemplateUpdateReqVO updateReqVO);

    /**
     * Delete decoration template
     *
     * @param id Number
     */
    void deleteDiyTemplate(Long id);

    /**
     * Get decoration template
     *
     * @param id Number
     * @return Decoration template
     */
    DiyTemplateDO getDiyTemplate(Long id);

    /**
     * Get the decoration template page
     *
     * @param pageReqVO Paged query
     * @return Decoration template paging
     */
    PageResult<DiyTemplateDO> getDiyTemplatePage(DiyTemplatePageReqVO pageReqVO);

    /**
     * Use decoration template
     *
     * @param id Number
     */
    void useDiyTemplate(Long id);

    /**
     * Update decoration template properties
     *
     * @param updateReqVO Update information
     */
    void updateDiyTemplateProperty(DiyTemplatePropertyUpdateRequestVO updateReqVO);

    /**
     * Get the decoration template in use
     *
     * @return Decoration template
     */
    DiyTemplateDO getUsedDiyTemplate();

}
