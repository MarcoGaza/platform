package cn.econets.blossom.module.system.service.dict;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.type.DictTypeSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.dict.DictTypeMapper;
import cn.econets.blossom.module.system.dal.dataobject.dict.DictTypeDO;
import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.*;

/**
 * Dictionary type Service Implementation class
 *
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageReqVO) {
        return dictTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeSaveReqVO createReqVO) {
        // Check the uniqueness of the name of the dictionary type
        validateDictTypeNameUnique(null, createReqVO.getName());
        // Check the uniqueness of dictionary type
        validateDictTypeUnique(null, createReqVO.getType());

        // Insert dictionary type
        DictTypeDO dictType = BeanUtils.toBean(createReqVO, DictTypeDO.class);
        dictType.setDeletedTime(LocalDateTimeUtils.EMPTY); // Unique Index，Avoid null Value
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeSaveReqVO updateReqVO) {
        // Verify own existence
        validateDictTypeExists(updateReqVO.getId());
        // Check the uniqueness of dictionary type names
        validateDictTypeNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // Check the uniqueness of dictionary type
        validateDictTypeUnique(updateReqVO.getId(), updateReqVO.getType());

        // Update dictionary type
        DictTypeDO updateObj = BeanUtils.toBean(updateReqVO, DictTypeDO.class);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // Check if it exists
        DictTypeDO dictType = validateDictTypeExists(id);
        // Check whether there is dictionary data
        if (dictDataService.getDictDataCountByDictType(dictType.getType()) > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }
        // Delete dictionary type
        dictTypeMapper.updateToDelete(id, LocalDateTime.now());
    }

    @Override
    public List<DictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        DictTypeDO dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Dictionary type
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictTypeDO dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Dictionary type
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    DictTypeDO validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictTypeDO dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
