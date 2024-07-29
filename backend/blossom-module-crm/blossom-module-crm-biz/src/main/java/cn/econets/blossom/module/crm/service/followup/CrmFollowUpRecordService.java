package cn.econets.blossom.module.crm.service.followup;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.followup.vo.CrmFollowUpRecordPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.followup.vo.CrmFollowUpRecordSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.followup.CrmFollowUpRecordDO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmFollowUpCreateReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Follow-up records Service Interface
 *
 */
public interface CrmFollowUpRecordService {

    /**
     * Create a follow-up record (Data permissions are based on bizType、 bizId)
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createFollowUpRecord(@Valid CrmFollowUpRecordSaveReqVO createReqVO);

    /**
     * Create more progress
     *
     * @param list Request
     */
    void createFollowUpRecordBatch(List<CrmFollowUpCreateReqBO> list);

    /**
     * Delete follow-up records (Data permissions are based on bizType、 bizId)
     *
     * @param id     Number
     * @param userId User Number
     */
    void deleteFollowUpRecord(Long id, Long userId);

    /**
     * Delete follow-up
     *
     * @param bizType Module type
     * @param bizId   Module data number
     */
    void deleteFollowUpRecordByBiz(Integer bizType, Long bizId);

    /**
     * Get follow-up records
     *
     * @param id Number
     * @return Follow-up records
     */
    CrmFollowUpRecordDO getFollowUpRecord(Long id);

    /**
     * Get follow-up record paging (Data permissions are based on bizType、 bizId)
     *
     * @param pageReqVO Paged query
     * @return Follow up record paging
     */
    PageResult<CrmFollowUpRecordDO> getFollowUpRecordPage(CrmFollowUpRecordPageReqVO pageReqVO);

    /**
     * Get follow-up records
     *
     * @param bizType Module type
     * @param bizIds  Module data number
     * @return Follow-up list
     */
    List<CrmFollowUpRecordDO> getFollowUpRecordByBiz(Integer bizType, Collection<Long> bizIds);

}
