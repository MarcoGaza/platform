package cn.econets.blossom.module.system.api.dict;

import cn.econets.blossom.module.system.api.dict.dto.DictDataRespDTO;

import java.util.Collection;

/**
 * Dictionary data API Interface
 *
 */
public interface DictDataApi {

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
     * Get the specified dictionary data，From cache
     *
     * @param type  Dictionary type
     * @param value Dictionary data value
     * @return Dictionary data
     */
    DictDataRespDTO getDictData(String type, String value);

    /**
     * Parse to obtain the specified dictionary data，From cache
     *
     * @param type  Dictionary type
     * @param label Dictionary data label
     * @return Dictionary data
     */
    DictDataRespDTO parseDictData(String type, String label);

}
