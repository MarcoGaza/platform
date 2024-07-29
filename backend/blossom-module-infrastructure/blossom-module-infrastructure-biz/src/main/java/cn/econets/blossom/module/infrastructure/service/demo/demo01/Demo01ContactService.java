package cn.econets.blossom.module.infrastructure.service.demo.demo01;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo01.Demo01ContactDO;

import javax.validation.Valid;

/**
 * Sample Contact Service Interface
 *
 *
 */
public interface Demo01ContactService {

    /**
     * Create sample contact
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDemo01Contact(@Valid Demo01ContactSaveReqVO createReqVO);

    /**
     * Update sample contact
     *
     * @param updateReqVO Update information
     */
    void updateDemo01Contact(@Valid Demo01ContactSaveReqVO updateReqVO);

    /**
     * Delete sample contact
     *
     * @param id Number
     */
    void deleteDemo01Contact(Long id);

    /**
     * Get sample contacts
     *
     * @param id Number
     * @return Sample Contact
     */
    Demo01ContactDO getDemo01Contact(Long id);

    /**
     * Get sample contact paging
     *
     * @param pageReqVO Paged query
     * @return Example Contacts Pagination
     */
    PageResult<Demo01ContactDO> getDemo01ContactPage(Demo01ContactPageReqVO pageReqVO);

}
