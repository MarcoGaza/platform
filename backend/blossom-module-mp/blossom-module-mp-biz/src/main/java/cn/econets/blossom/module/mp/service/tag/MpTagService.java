package cn.econets.blossom.module.mp.service.tag;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagUpdateReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.tag.MpTagDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Public account tag Service Interface
 *
 *
 */
public interface MpTagService {

    /**
     * Create a public account tag
     *
     * @param createReqVO Create tag information
     * @return Tag number
     */
    Long createTag(@Valid MpTagCreateReqVO createReqVO);

    /**
     * Update public account tags
     *
     * @param updateReqVO Update tag information
     */
    void updateTag(@Valid MpTagUpdateReqVO updateReqVO);

    /**
     * Delete the public account tag
     *
     * @param id    Number
     */
    void deleteTag(Long id);

    /**
     * Get the public account tag paging
     *
     * @param pageReqVO Paged query
     * @return Paging of public account tags
     */
    PageResult<MpTagDO> getTagPage(MpTagPageReqVO pageReqVO);

    /**
     * Get the public account tag details
     * @param id idQuery
     * @return Public account tag details
     */
    MpTagDO get(Long id);

    List<MpTagDO> getTagList();

    /**
     * Synchronize public account tags
     *
     * @param accountId The public account number
     */
    void syncTag(Long accountId);

}
