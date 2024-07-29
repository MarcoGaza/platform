package cn.econets.blossom.module.system.service.dict;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.type.DictTypeSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.dict.DictTypeDO;

import java.util.List;

/**
 * Dictionary type Service Interface
 *
 */
public interface DictTypeService {

    /**
     * Create dictionary type
     *
     * @param createReqVO Dictionary type information
     * @return Dictionary type number
     */
    Long createDictType(DictTypeSaveReqVO createReqVO);

    /**
     * Update dictionary type
     *
     * @param updateReqVO Dictionary type information
     */
    void updateDictType(DictTypeSaveReqVO updateReqVO);

    /**
     * Delete dictionary type
     *
     * @param id Dictionary type number
     */
    void deleteDictType(Long id);

    /**
     * Get dictionary type pagination list
     *
     * @param pageReqVO Pagination request
     * @return Dictionary type pagination list
     */
    PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageReqVO);

    /**
     * Get dictionary type details
     *
     * @param id Dictionary type number
     * @return Dictionary type
     */
    DictTypeDO getDictType(Long id);

    /**
     * Get dictionary type details
     *
     * @param type Dictionary type
     * @return Dictionary type details
     */
    DictTypeDO getDictType(String type);

    /**
     * Get a list of all dictionary types
     *
     * @return Dictionary type list
     */
    List<DictTypeDO> getDictTypeList();

}
