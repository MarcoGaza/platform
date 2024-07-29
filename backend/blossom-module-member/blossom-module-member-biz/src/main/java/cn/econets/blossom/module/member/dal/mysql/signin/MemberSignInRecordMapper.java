package cn.econets.blossom.module.member.dal.mysql.signin;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Sign-in record Mapper
 *
 * 
 */
@Mapper
public interface MemberSignInRecordMapper extends BaseMapperX<MemberSignInRecordDO> {

    default PageResult<MemberSignInRecordDO> selectPage(MemberSignInRecordPageReqVO reqVO, Set<Long> userIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberSignInRecordDO>()
                .inIfPresent(MemberSignInRecordDO::getUserId, userIds)
                .eqIfPresent(MemberSignInRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MemberSignInRecordDO::getDay, reqVO.getDay())
                .betweenIfPresent(MemberSignInRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberSignInRecordDO::getId));
    }

    default PageResult<MemberSignInRecordDO> selectPage(Long userId, PageParam pageParam) {
        return selectPage(pageParam, new LambdaQueryWrapperX<MemberSignInRecordDO>()
                .eq(MemberSignInRecordDO::getUserId, userId)
                .orderByDesc(MemberSignInRecordDO::getId));
    }

    /**
     * Get the user's most recent check-in record information，In reverse order according to check-in time
     *
     * @param userId User Number
     * @return Sign-in record list
     */
    default MemberSignInRecordDO selectLastRecordByUserId(Long userId) {
        return selectOne(new QueryWrapper<MemberSignInRecordDO>()
                .eq("user_id", userId)
                .orderByDesc("create_time")
                .last("limit 1"));
    }

    default Long selectCountByUserId(Long userId) {
        return selectCount(MemberSignInRecordDO::getUserId, userId);
    }

    /**
     * Get the user's check-in record list information
     *
     * @param userId User Number
     * @return Sign-in record information
     */
    default List<MemberSignInRecordDO> selectListByUserId(Long userId) {
        return selectList(MemberSignInRecordDO::getUserId, userId);
    }

}
