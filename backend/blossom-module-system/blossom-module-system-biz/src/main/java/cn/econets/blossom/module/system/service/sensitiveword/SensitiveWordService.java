package cn.econets.blossom.module.system.service.sensitiveword;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordSaveVO;
import cn.econets.blossom.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Sensitive words Service Interface
 *
 */
public interface SensitiveWordService {

    /**
     * Create sensitive words
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSensitiveWord(@Valid SensitiveWordSaveVO createReqVO);

    /**
     * Update sensitive words
     *
     * @param updateReqVO Update information
     */
    void updateSensitiveWord(@Valid SensitiveWordSaveVO updateReqVO);

    /**
     * Delete sensitive words
     *
     * @param id Number
     */
    void deleteSensitiveWord(Long id);

    /**
     * Get sensitive words
     *
     * @param id Number
     * @return Sensitive words
     */
    SensitiveWordDO getSensitiveWord(Long id);

    /**
     * Get sensitive word list
     *
     * @return Sensitive word list
     */
    List<SensitiveWordDO> getSensitiveWordList();

    /**
     * Get sensitive word paging
     *
     * @param pageReqVO Paged query
     * @return Sensitive word paging
     */
    PageResult<SensitiveWordDO> getSensitiveWordPage(SensitiveWordPageReqVO pageReqVO);

    /**
     * Get the label array of all sensitive words
     *
     * @return Tag array
     */
    Set<String> getSensitiveWordTagSet();

    /**
     * Get the array of illegal sensitive words contained in the text
     *
     * @param text Text
     * @param tags Tag array
     * @return Invalid sensitive word array
     */
    List<String> validateText(String text, List<String> tags);

    /**
     * Judge whether the text contains sensitive words
     *
     * @param text Text
     * @param tags Tag array
     * @return Whether it contains sensitive words
     */
    boolean isTextValid(String text, List<String> tags);

}
