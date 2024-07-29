package cn.econets.blossom.module.crm.service.clue;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmCluePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueTransferReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueTranslateReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.clue.CrmClueDO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Clues Service Interface
 *
 */
public interface CrmClueService {

    /**
     * Create clue
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createClue(@Valid CrmClueSaveReqVO createReqVO, Long userId);

    /**
     * Update clues
     *
     * @param updateReqVO Update information
     */
    void updateClue(@Valid CrmClueSaveReqVO updateReqVO);

    /**
     * Update follow-up information related to the clue
     *
     * @param clueUpdateFollowUpReqBO Information
     */
    void updateClueFollowUp(CrmUpdateFollowUpReqBO clueUpdateFollowUpReqBO);

    /**
     * Delete clues
     *
     * @param id Number
     */
    void deleteClue(Long id);

    /**
     * Get clues
     *
     * @param id Number
     * @return Clues
     */
    CrmClueDO getClue(Long id);

    /**
     * Get clue list
     *
     * @param ids Number
     * @return Clue list
     */
    List<CrmClueDO> getClueList(Collection<Long> ids, Long userId);

    /**
     * Get clues page
     *
     * @param pageReqVO Paged query
     * @param userId    User ID
     * @return Clue paging
     */
    PageResult<CrmClueDO> getCluePage(CrmCluePageReqVO pageReqVO, Long userId);

    /**
     * Cue transfer
     *
     * @param reqVO  Request
     * @param userId User Number
     */
    void transferClue(CrmClueTransferReqVO reqVO, Long userId);

    /**
     * Convert leads into customers
     *
     * @param reqVO  Clue number
     * @param userId User Number
     */
    void translateCustomer(CrmClueTranslateReqVO reqVO, Long userId);

}
