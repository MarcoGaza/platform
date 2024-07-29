package cn.econets.blossom.module.system.service.dept;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.econets.blossom.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.dept.DeptMapper;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.*;

/**
 * Department Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries Clear all caches，Because of operating a department，Involving multiple caches
    public Long createDept(DeptSaveReqVO createReqVO) {
        if (createReqVO.getParentId() == null) {
            createReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // Check the validity of the parent department
        validateParentDept(null, createReqVO.getParentId());
        // Verify the uniqueness of department name
        validateDeptNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // Insert department
        DeptDO dept = BeanUtils.toBean(createReqVO, DeptDO.class);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries Clear all caches，Because of operating a department，Involving multiple caches
    public void updateDept(DeptSaveReqVO updateReqVO) {
        if (updateReqVO.getParentId() == null) {
            updateReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // Verify own existence
        validateDeptExists(updateReqVO.getId());
        // Check the validity of the parent department
        validateParentDept(updateReqVO.getId(), updateReqVO.getParentId());
        // Verify the uniqueness of department name
        validateDeptNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // Update Department
        DeptDO updateObj = BeanUtils.toBean(updateReqVO, DeptDO.class);
        deptMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries Clear all caches，Because of operating a department，Involving multiple caches
    public void deleteDept(Long id) {
        // Check if it exists
        validateDeptExists(id);
        // Check whether there is a sub-department
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // Delete department
        deptMapper.deleteById(id);
    }

    @VisibleForTesting
    void validateDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    @VisibleForTesting
    void validateParentDept(Long id, Long parentId) {
        if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. Cannot set itself as parent department
        if (Objects.equals(id, parentId)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 2. The parent department does not exist
        DeptDO parentDept = deptMapper.selectById(parentId);
        if (parentDept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 3. Recursively check the parent department，If the parent department is its own child department，An error is reported，Avoid loops
        if (id == null) { // id Empty，Added description，No need to consider loops
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 Verification loop
            parentId = parentDept.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEPT_PARENT_IS_CHILD);
            }
            // 3.2 Continue recursively to the next level parent department
            if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if (parentDept == null) {
                break;
            }
        }
    }

    @VisibleForTesting
    void validateDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO dept = deptMapper.selectByParentIdAndName(parentId, name);
        if (dept == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Department
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dept.getId(), id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptDO> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeptDO> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> list = deptMapper.selectList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return list;
    }

    @Override
    public List<DeptDO> getChildDeptList(Long id) {
        List<DeptDO> children = new LinkedList<>();
        // Traverse each layer
        Collection<Long> parentIds = Collections.singleton(id);
        for (int i = 0; i < Short.MAX_VALUE; i++) { // Use Short.MAX_VALUE Avoid bug Scene，There is an infinite loop
            // Query the current layer，All sub-departments
            List<DeptDO> depts = deptMapper.selectListByParentId(parentIds);
            // 1. If there is no sub-department，Then end the traversal
            if (CollUtil.isEmpty(depts)) {
                break;
            }
            // 2. If there are sub-departments，Continue traversal
            children.addAll(depts);
            parentIds = CollectionUtils.convertSet(depts, DeptDO::getId);
        }
        return children;
    }

    @Override
    @DataPermission(enable = false) // Disable data permissions，Avoid incorrect cache creation
    @Cacheable(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST, key = "#id")
    public Set<Long> getChildDeptIdListFromCache(Long id) {
        List<DeptDO> children = getChildDeptList(id);
        return CollectionUtils.convertSet(children, DeptDO::getId);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // Get department information
        Map<Long, DeptDO> deptMap = getDeptMap(ids);
        // Verification
        ids.forEach(id -> {
            DeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

}
