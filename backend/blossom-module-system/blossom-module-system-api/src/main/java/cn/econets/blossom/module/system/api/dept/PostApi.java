package cn.econets.blossom.module.system.api.dept;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.api.dept.dto.PostRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Position API Interface
 *
 */
public interface PostApi {

    /**
     * Check whether positions are valid。As follows，Deemed invalid：
     * 1. Position number does not exist
     * 2. Position is disabled
     *
     * @param ids Position number array
     */
    void validPostList(Collection<Long> ids);

    List<PostRespDTO> getPostList(Collection<Long> ids);

    default Map<Long, PostRespDTO> getPostMap(Collection<Long> ids) {
        List<PostRespDTO> list = getPostList(ids);
        return CollectionUtils.convertMap(list, PostRespDTO::getId);
    }

}
