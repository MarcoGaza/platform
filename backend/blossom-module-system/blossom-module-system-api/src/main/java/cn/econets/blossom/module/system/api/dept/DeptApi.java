package cn.econets.blossom.module.system.api.dept;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Department API Interface
 *
 */
public interface DeptApi {

    /**
     * Get department information
     *
     * @param id Department Number
     * @return Department Information
     */
    DeptRespDTO getDept(Long id);

    /**
     * Get department information array
     *
     * @param ids Department number array
     * @return Department information array
     */
    List<DeptRespDTO> getDeptList(Collection<Long> ids);

    /**
     * Check whether the departments are valid。As follows，Deemed invalid：
     * 1. Department number does not exist
     * 2. Department is disabled
     *
     * @param ids Role number array
     */
    void validateDeptList(Collection<Long> ids);

    /**
     * Get the department with the specified number Map
     *
     * @param ids Department number array
     * @return Department Map
     */
    default Map<Long, DeptRespDTO> getDeptMap(Collection<Long> ids) {
        List<DeptRespDTO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptRespDTO::getId);
    }

    /**
     * Get all sub-departments of the specified department
     *
     * @param id Department Number
     * @return Sub-department list
     */
    List<DeptRespDTO> getChildDeptList(Long id);
}
