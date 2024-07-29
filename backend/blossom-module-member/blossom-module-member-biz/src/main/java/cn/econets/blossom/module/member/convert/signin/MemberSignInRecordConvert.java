package cn.econets.blossom.module.member.convert.signin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.MapUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordRespVO;
import cn.econets.blossom.module.member.controller.app.signin.vo.record.AppMemberSignInRecordRespVO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Sign-in record Convert
 *
 * 
 */
@Mapper
public interface MemberSignInRecordConvert {

    MemberSignInRecordConvert INSTANCE = Mappers.getMapper(MemberSignInRecordConvert.class);

    default PageResult<MemberSignInRecordRespVO> convertPage(PageResult<MemberSignInRecordDO> pageResult, List<MemberUserDO> users) {
        PageResult<MemberSignInRecordRespVO> voPageResult = convertPage(pageResult);
        // user Splicing
        Map<Long, MemberUserDO> userMap = convertMap(users, MemberUserDO::getId);
        voPageResult.getList().forEach(record -> MapUtils.findAndThen(userMap, record.getUserId(),
                memberUserRespDTO -> record.setNickname(memberUserRespDTO.getNickname())));
        return voPageResult;
    }

    PageResult<MemberSignInRecordRespVO> convertPage(PageResult<MemberSignInRecordDO> pageResult);

    PageResult<AppMemberSignInRecordRespVO> convertPage02(PageResult<MemberSignInRecordDO> pageResult);

    AppMemberSignInRecordRespVO coverRecordToAppRecordVo(MemberSignInRecordDO memberSignInRecordDO);

    default MemberSignInRecordDO convert(Long userId, MemberSignInRecordDO lastRecord, List<MemberSignInConfigDO> configs) {
        // 1. Calculate the number of days to sign in
        configs.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        MemberSignInConfigDO lastConfig = CollUtil.getLast(configs); // Maximum sign-in days configuration
        // 1.2. Calculate today's check-in date (Only continuous sign-in will add, otherwise reset to 1)
        int day = 1;
        if (lastRecord != null && DateUtils.isYesterday(lastRecord.getCreateTime())) {
            day = lastRecord.getDay() + 1;
        }
        // 1.3 Judge whether the maximum sign-in configuration has been exceeded
        if (day > lastConfig.getDay()) {
            day = 1; // Exceeded the maximum number of configured days，Reset to Day 1。(This means the next round of sign-in will begin)
        }

        // 2.1 Initialize sign-in information
        MemberSignInRecordDO record = new MemberSignInRecordDO().setUserId(userId)
                .setDay(day).setPoint(0).setExperience(0);
        // 2.2 Get the points corresponding to sign-in
        MemberSignInConfigDO config = CollUtil.findOne(configs, item -> ObjUtil.equal(item.getDay(), record.getDay()));
        if (config == null) {
            return record;
        }
        record.setPoint(config.getPoint());
        record.setExperience(config.getExperience());
        return record;
    }

}
