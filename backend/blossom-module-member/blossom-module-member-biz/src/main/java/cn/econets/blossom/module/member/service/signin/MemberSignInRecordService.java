package cn.econets.blossom.module.member.service.signin;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import cn.econets.blossom.module.member.controller.app.signin.vo.record.AppMemberSignInRecordSummaryRespVO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInRecordDO;

/**
 * Sign-in record Service Interface
 *
 * 
 */
public interface MemberSignInRecordService {

    /**
     * 【Administrator】Get the sign-in record page
     *
     * @param pageReqVO Paged query
     * @return Sign-in record paging
     */
    PageResult<MemberSignInRecordDO> getSignInRecordPage(MemberSignInRecordPageReqVO pageReqVO);

    /**
     * 【Member】Get the sign-in record page
     *
     * @param userId    User Number
     * @param pageParam Paged query
     * @return Sign-in record paging
     */
    PageResult<MemberSignInRecordDO> getSignRecordPage(Long userId, PageParam pageParam);

    /**
     * Create a sign-in record
     *
     * @param userId User Number
     * @return Sign-in record
     */
    MemberSignInRecordDO createSignRecord(Long userId);

    /**
     * Based on user number，Get personal sign-in statistics
     *
     * @param userId User Number
     * @return Personal check-in statistics
     */
    AppMemberSignInRecordSummaryRespVO getSignInRecordSummary(Long userId);


}
