package cn.econets.blossom.module.system.service.dept;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import cn.econets.blossom.module.system.controller.admin.dept.vo.post.PostSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.dept.PostDO;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Position Service Interface
 *
 */
public interface PostService {

    /**
     * Create a position
     *
     * @param createReqVO Job Information
     * @return Position number
     */
    Long createPost(PostSaveReqVO createReqVO);

    /**
     * Update position
     *
     * @param updateReqVO Job Information
     */
    void updatePost(PostSaveReqVO updateReqVO);

    /**
     * Delete job information
     *
     * @param id Position number
     */
    void deletePost(Long id);

    /**
     * Get the job list
     *
     * @param ids Position number array
     * @return Department List
     */
    List<PostDO> getPostList(@Nullable Collection<Long> ids);

    /**
     * Get a list of eligible positions
     *
     * @param ids Position number array。If empty，No filtering
     * @param statuses Status array。If empty，No filtering
     * @return Department List
     */
    List<PostDO> getPostList(@Nullable Collection<Long> ids,
                             @Nullable Collection<Integer> statuses);

    /**
     * Get the paginated list of positions
     *
     * @param reqVO Pagination conditions
     * @return Department pagination list
     */
    PageResult<PostDO> getPostPage(PostPageReqVO reqVO);

    /**
     * Get job information
     *
     * @param id Position number
     * @return Job Information
     */
    PostDO getPost(Long id);

    /**
     * Check whether positions are valid。As follows，Deemed invalid：
     * 1. Position number does not exist
     * 2. Position is disabled
     *
     * @param ids Position number array
     */
    void validatePostList(Collection<Long> ids);

}
