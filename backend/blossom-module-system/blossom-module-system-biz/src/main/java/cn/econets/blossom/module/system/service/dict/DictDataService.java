package cn.econets.blossom.module.system.service.dict;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.dict.DictDataDO;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Dictionary data Service Interface
 *
 */
public interface DictDataService {

    /**
     * Create dictionary data
     *
     * @param createReqVO Dictionary data information
     * @return Dictionary data number
     */
    Long createDictData(DictDataSaveReqVO createReqVO);

    /**
     * Update dictionary data
     *
     * @param updateReqVO Dictionary data information
     */
    void updateDictData(DictDataSaveReqVO updateReqVO);

    /**
     * Delete dictionary data
     *
     * @param id Dictionary data number
     */
    void deleteDictData(Long id);

    /**
     * Get dictionary data list
     *
     * @param status   Status
     * @param dictType Dictionary type
     * @return Full list of dictionary data
     */
    List<DictDataDO> getDictDataList(@Nullable Integer status, @Nullable String dictType);

    /**
     * Get the paginated list of dictionary data
     *
     * @param pageReqVO Pagination request
     * @return Dictionary data page list
     */
    PageResult<DictDataDO> getDictDataPage(DictDataPageReqVO pageReqVO);

    /**
     * Get dictionary data details
     *
     * @param id Dictionary data number
     * @return Dictionary data
     */
    DictDataDO getDictData(Long id);

    /**
     * Get the number of data of the specified dictionary type
     *
     * @param dictType Dictionary type
     * @return Number of data
     */
    long getDictDataCountByDictType(String dictType);

    /**
     * Check if the dictionary data is valid。The following situations，Deemed invalid：
     * 1. Dictionary data does not exist
     * 2. Dictionary data is disabled
     *
     * @param dictType Dictionary type
     * @param values   Array of dictionary data values
     */
    void validateDictDataList(String dictType, Collection<String> values);

    /**
     * Get the specified dictionary data
     *
     * @param dictType Dictionary type
     * @param value    Dictionary data value
     * @return Dictionary data
     */
    DictDataDO getDictData(String dictType, String value);

    /**
     * Parse to obtain the specified dictionary data，From cache
     *
     * @param dictType Dictionary type
     * @param label    Dictionary data label
     * @return Dictionary data
     */
    DictDataDO parseDictData(String dictType, String label);

}
