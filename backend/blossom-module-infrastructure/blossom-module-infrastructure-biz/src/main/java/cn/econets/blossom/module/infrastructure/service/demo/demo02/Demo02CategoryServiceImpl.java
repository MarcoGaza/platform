package cn.econets.blossom.module.infrastructure.service.demo.demo02;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategoryListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategorySaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo02.Demo02CategoryDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.demo.demo02.Demo02CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.*;

/**
 * Example Category Service Implementation class
 *
 *
 */
@Service
@Validated
public class Demo02CategoryServiceImpl implements Demo02CategoryService {

    @Resource
    private Demo02CategoryMapper demo02CategoryMapper;

    @Override
    public Long createDemo02Category(Demo02CategorySaveReqVO createReqVO) {
        // Check the validity of the parent number
        validateParentDemo02Category(null, createReqVO.getParentId());
        // Check the uniqueness of the name
        validateDemo02CategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // Insert
        Demo02CategoryDO demo02Category = BeanUtils.toBean(createReqVO, Demo02CategoryDO.class);
        demo02CategoryMapper.insert(demo02Category);
        // Return
        return demo02Category.getId();
    }

    @Override
    public void updateDemo02Category(Demo02CategorySaveReqVO updateReqVO) {
        // Check existence
        validateDemo02CategoryExists(updateReqVO.getId());
        // Check the validity of the parent number
        validateParentDemo02Category(updateReqVO.getId(), updateReqVO.getParentId());
        // Check the uniqueness of the name
        validateDemo02CategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // Update
        Demo02CategoryDO updateObj = BeanUtils.toBean(updateReqVO, Demo02CategoryDO.class);
        demo02CategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteDemo02Category(Long id) {
        // Check existence
        validateDemo02CategoryExists(id);
        // Check whether there is a sub-example classification
        if (demo02CategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(DEMO02_CATEGORY_EXITS_CHILDREN);
        }
        // Delete
        demo02CategoryMapper.deleteById(id);
    }

    private void validateDemo02CategoryExists(Long id) {
        if (demo02CategoryMapper.selectById(id) == null) {
            throw exception(DEMO02_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentDemo02Category(Long id, Long parentId) {
        if (parentId == null || Demo02CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. Cannot set itself as the parent example category
        if (Objects.equals(id, parentId)) {
            throw exception(DEMO02_CATEGORY_PARENT_ERROR);
        }
        // 2. The parent example category does not exist
        Demo02CategoryDO parentDemo02Category = demo02CategoryMapper.selectById(parentId);
        if (parentDemo02Category == null) {
            throw exception(DEMO02_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. Recursively check the parent example category，If the parent example category is its own child example category，An error is reported，Avoid loops
        if (id == null) { // id Empty，Additional description，No need to consider loops
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 Verification loop
            parentId = parentDemo02Category.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEMO02_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 Continue recursively to the next level of parent example classification
            if (parentId == null || Demo02CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDemo02Category = demo02CategoryMapper.selectById(parentId);
            if (parentDemo02Category == null) {
                break;
            }
        }
    }

    private void validateDemo02CategoryNameUnique(Long id, Long parentId, String name) {
        Demo02CategoryDO demo02Category = demo02CategoryMapper.selectByParentIdAndName(parentId, name);
        if (demo02Category == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Example Classification
        if (id == null) {
            throw exception(DEMO02_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(demo02Category.getId(), id)) {
            throw exception(DEMO02_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public Demo02CategoryDO getDemo02Category(Long id) {
        return demo02CategoryMapper.selectById(id);
    }

    @Override
    public List<Demo02CategoryDO> getDemo02CategoryList(Demo02CategoryListReqVO listReqVO) {
        return demo02CategoryMapper.selectList(listReqVO);
    }

}
