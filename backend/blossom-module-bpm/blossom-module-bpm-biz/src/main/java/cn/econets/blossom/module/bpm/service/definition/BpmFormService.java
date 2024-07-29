package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormUpdateReqVO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmFormDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Dynamic Form Service Interface
 *
 */
public interface BpmFormService {

    /**
     * Create a dynamic form
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createForm(@Valid BpmFormCreateReqVO createReqVO);

    /**
     * Update dynamic form
     *
     * @param updateReqVO Update information
     */
    void updateForm(@Valid BpmFormUpdateReqVO updateReqVO);

    /**
     * Delete dynamic form
     *
     * @param id Number
     */
    void deleteForm(Long id);

    /**
     * Get dynamic form
     *
     * @param id Number
     * @return Dynamic Form
     */
    BpmFormDO getForm(Long id);

    /**
     * Get dynamic form list
     *
     * @return Dynamic form list
     */
    List<BpmFormDO> getFormList();

    /**
     * Get dynamic form list
     *
     * @param ids Number
     * @return Dynamic form list
     */
    List<BpmFormDO> getFormList(Collection<Long> ids);

    /**
     * Get dynamic form Map
     *
     * @param ids Number
     * @return Dynamic Form Map
     */
    default Map<Long, BpmFormDO> getFormMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return CollectionUtils.convertMap(this.getFormList(ids), BpmFormDO::getId);
    }

    /**
     * Get dynamic form paging
     *
     * @param pageReqVO Paged query
     * @return Dynamic form paging
     */
    PageResult<BpmFormDO> getFormPage(BpmFormPageReqVO pageReqVO);

    /**
     * Verification process form has been configured
     *
     * @param configStr  configStr Field
     * @return Process Form
     */
    BpmFormDO checkFormConfig(String  configStr);

}
