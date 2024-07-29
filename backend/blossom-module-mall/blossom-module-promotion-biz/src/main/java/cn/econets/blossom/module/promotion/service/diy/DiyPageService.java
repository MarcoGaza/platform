package cn.econets.blossom.module.promotion.service.diy;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPageCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPagePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPagePropertyUpdateRequestVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPageUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyPageDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Decoration page Service Interface
 *
 */
public interface DiyPageService {

    /**
     * Create decoration page
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDiyPage(@Valid DiyPageCreateReqVO createReqVO);

    /**
     * Update decoration page
     *
     * @param updateReqVO Update information
     */
    void updateDiyPage(@Valid DiyPageUpdateReqVO updateReqVO);

    /**
     * Delete the decoration page
     *
     * @param id Number
     */
    void deleteDiyPage(Long id);

    /**
     * Get the decoration page
     *
     * @param id Number
     * @return Decoration page
     */
    DiyPageDO getDiyPage(Long id);

    /**
     * Get the decoration page list
     *
     * @param ids Number
     * @return Decoration page list
     */
    List<DiyPageDO> getDiyPageList(Collection<Long> ids);

    /**
     * Get the decoration page pagination
     *
     * @param pageReqVO Paged query
     * @return Decoration page pagination
     */
    PageResult<DiyPageDO> getDiyPagePage(DiyPagePageReqVO pageReqVO);

    /**
     * Update decoration page properties
     *
     * @param updateReqVO Update information
     */
    void updateDiyPageProperty(DiyPagePropertyUpdateRequestVO updateReqVO);

    /**
     * Get the page list to which the template belongs
     *
     * @param templateId Template number
     * @return Decoration page list
     */
    List<DiyPageDO> getDiyPageByTemplateId(Long templateId);

}
