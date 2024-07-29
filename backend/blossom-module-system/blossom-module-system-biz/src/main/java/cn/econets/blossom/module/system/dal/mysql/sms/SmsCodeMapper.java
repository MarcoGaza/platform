package cn.econets.blossom.module.system.dal.mysql.sms;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.QueryWrapperX;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsCodeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsCodeMapper extends BaseMapperX<SmsCodeDO> {

    /**
     * Get the last verification code of the mobile phone number
     *
     * @param mobile Mobile phone number
     * @param scene Send scene，Optional
     * @param code Verification code Optional
     * @return Mobile verification code
     */
    default SmsCodeDO selectLastByMobile(String mobile, String code, Integer scene) {
        return selectOne(new QueryWrapperX<SmsCodeDO>()
                .eq("mobile", mobile)
                .eqIfPresent("scene", scene)
                .eqIfPresent("code", code)
                .orderByDesc("id")
                .limitN(1));
    }

}
