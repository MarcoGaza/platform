package cn.econets.blossom.module.member.service.tag;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.tag.MemberTagDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Member Tag Service Interface
 *
 * 
 */
public interface MemberTagService {

    /**
     * Create member tag
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createTag(@Valid MemberTagCreateReqVO createReqVO);

    /**
     * Update member tags
     *
     * @param updateReqVO Update information
     */
    void updateTag(@Valid MemberTagUpdateReqVO updateReqVO);

    /**
     * Delete member tag
     *
     * @param id Number
     */
    void deleteTag(Long id);

    /**
     * Get member tag
     *
     * @param id Number
     * @return Member Tag
     */
    MemberTagDO getTag(Long id);

    /**
     * Get member tag list
     *
     * @param ids Number
     * @return Member tag list
     */
    List<MemberTagDO> getTagList(Collection<Long> ids);

    /**
     * Get member tag paging
     *
     * @param pageReqVO Paged query
     * @return Member tag paging
     */
    PageResult<MemberTagDO> getTagPage(MemberTagPageReqVO pageReqVO);

    /**
     * Get tag list
     *
     * @return Tag list
     */
    List<MemberTagDO> getTagList();

}
