package cn.econets.blossom.module.system.service.dept;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.econets.blossom.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Department Service Interface
 *
 */
public interface DeptService {

    /**
     * Create a department
     *
     * @param createReqVO Department Information
     * @return Department Number
     */
    Long createDept(DeptSaveReqVO createReqVO);

    /**
     * Update Department
     *
     * @param updateReqVO Department Information
     */
    void updateDept(DeptSaveReqVO updateReqVO);

    /**
     * Delete department
     *
     * @param id Department Number
     */
    void deleteDept(Long id);

    /**
     * Get department information
     *
     * @param id Department Number
     * @return Department Information
     */
    DeptDO getDept(Long id);

    /**
     * Get department information array
     *
     * @param ids Department number array
     * @return Department information array
     */
    List<DeptDO> getDeptList(Collection<Long> ids);

    /**
     * Filter department list
     *
     * @param reqVO Filter condition request VO
     * @return Department List
     */
    List<DeptDO> getDeptList(DeptListReqVO reqVO);

    /**
     * Get the department with the specified number Map
     *
     * @param ids Department number array
     * @return Department Map
     */
    default Map<Long, DeptDO> getDeptMap(Collection<Long> ids) {
        List<DeptDO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptDO::getId);
    }

    /**
     * Get all sub-departments of the specified department
     *
     * @param id Department Number
     * @return Sub-department list
     */
    List<DeptDO> getChildDeptList(Long id);

    /**
     * Get all sub-departments，From cache
     *
     * @param id Parent department number
     * @return Sub-department list
     */
    Set<Long> getChildDeptIdListFromCache(Long id);

    /**
     * Check whether the departments are valid。As follows，Deemed invalid：
     * 1. Department number does not exist
     * 2. Department is disabled
     *
     * @param ids Role number array
     */
    void validateDeptList(Collection<Long> ids);

}
