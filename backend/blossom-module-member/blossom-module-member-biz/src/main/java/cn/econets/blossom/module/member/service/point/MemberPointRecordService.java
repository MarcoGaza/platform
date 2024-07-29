package cn.econets.blossom.module.member.service.point;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.point.vo.recrod.MemberPointRecordPageReqVO;
import cn.econets.blossom.module.member.controller.app.point.vo.AppMemberPointRecordPageReqVO;
import cn.econets.blossom.module.member.dal.dataobject.point.MemberPointRecordDO;
import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;

/**
 * User points record Service Interface
 *
 * 
 */
public interface MemberPointRecordService {

    /**
     * 【Administrator】Get points record paging
     *
     * @param pageReqVO Paged query
     * @return Sign-in record paging
     */
    PageResult<MemberPointRecordDO> getPointRecordPage(MemberPointRecordPageReqVO pageReqVO);

    /**
     * 【Member】Get points record paging
     *
     * @param userId User Number
     * @param pageReqVO Paged query
     * @return Sign-in record paging
     */
    PageResult<MemberPointRecordDO> getPointRecordPage(Long userId, AppMemberPointRecordPageReqVO pageReqVO);

    /**
     * Create user points record
     *
     * @param userId  UserID
     * @param point   Change Points
     * @param bizType Business Type
     * @param bizId   Business Number
     */
    void createPointRecord(Long userId, Integer point, MemberPointBizTypeEnum bizType, String bizId);
}
