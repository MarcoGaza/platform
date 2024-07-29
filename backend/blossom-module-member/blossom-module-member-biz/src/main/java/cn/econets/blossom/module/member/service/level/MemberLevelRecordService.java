package cn.econets.blossom.module.member.service.level;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.level.vo.record.MemberLevelRecordPageReqVO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelRecordDO;

/**
 * Member level record Service Interface
 *
 * 
 */
public interface MemberLevelRecordService {

    /**
     * Get member level record
     *
     * @param id Number
     * @return Member level record
     */
    MemberLevelRecordDO getLevelRecord(Long id);

    /**
     * Get member level record paging
     *
     * @param pageReqVO Paged query
     * @return Member Level Record Paging
     */
    PageResult<MemberLevelRecordDO> getLevelRecordPage(MemberLevelRecordPageReqVO pageReqVO);

    /**
     * Create member level record
     *
     * @param levelRecord Member level record
     */
    void createLevelRecord(MemberLevelRecordDO levelRecord);

}
