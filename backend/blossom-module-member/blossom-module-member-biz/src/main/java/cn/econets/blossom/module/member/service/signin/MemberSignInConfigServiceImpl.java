package cn.econets.blossom.module.member.service.signin;

import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import cn.econets.blossom.module.member.convert.signin.MemberSignInConfigConvert;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.econets.blossom.module.member.dal.mysql.signin.MemberSignInConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.SIGN_IN_CONFIG_EXISTS;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.SIGN_IN_CONFIG_NOT_EXISTS;

/**
 * Sign-in rules Service Implementation class
 *
 * 
 */
@Service
@Validated
public class MemberSignInConfigServiceImpl implements MemberSignInConfigService {

    @Resource
    private MemberSignInConfigMapper memberSignInConfigMapper;

    @Override
    public Long createSignInConfig(MemberSignInConfigCreateReqVO createReqVO) {
        // Judge whether to insert the sign-in days repeatedly
        validateSignInConfigDayDuplicate(createReqVO.getDay(), null);

        // Insert
        MemberSignInConfigDO signInConfig = MemberSignInConfigConvert.INSTANCE.convert(createReqVO);
        memberSignInConfigMapper.insert(signInConfig);
        // Return
        return signInConfig.getId();
    }

    @Override
    public void updateSignInConfig(MemberSignInConfigUpdateReqVO updateReqVO) {
        // Check existence
        validateSignInConfigExists(updateReqVO.getId());
        // Judge whether to insert the sign-in days repeatedly
        validateSignInConfigDayDuplicate(updateReqVO.getDay(), updateReqVO.getId());

        // Judge update
        MemberSignInConfigDO updateObj = MemberSignInConfigConvert.INSTANCE.convert(updateReqVO);
        memberSignInConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteSignInConfig(Long id) {
        // Check existence
        validateSignInConfigExists(id);
        // Delete
        memberSignInConfigMapper.deleteById(id);
    }

    private void validateSignInConfigExists(Long id) {
        if (memberSignInConfigMapper.selectById(id) == null) {
            throw exception(SIGN_IN_CONFIG_NOT_EXISTS);
        }
    }

    /**
     * Verification day Repeat
     *
     * @param day Sky
     * @param id  Number，Only when updated will it be passed
     */
    private void validateSignInConfigDayDuplicate(Integer day, Long id) {
        MemberSignInConfigDO config = memberSignInConfigMapper.selectByDay(day);
        // 1. When adding，config Not empty，It means it is repeated
        if (id == null && config != null) {
            throw exception(SIGN_IN_CONFIG_EXISTS);
        }
        // 2. Updating，If config Not empty，and id Not equal，It means it is repeated
        if (id != null && config != null && !config.getId().equals(id)) {
            throw exception(SIGN_IN_CONFIG_EXISTS);
        }
    }

    @Override
    public MemberSignInConfigDO getSignInConfig(Long id) {
        return memberSignInConfigMapper.selectById(id);
    }

    @Override
    public List<MemberSignInConfigDO> getSignInConfigList() {
        List<MemberSignInConfigDO> list = memberSignInConfigMapper.selectList();
        list.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        return list;
    }

    @Override
    public List<MemberSignInConfigDO> getSignInConfigList(Integer status) {
        List<MemberSignInConfigDO> list = memberSignInConfigMapper.selectListByStatus(status);
        list.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        return list;
    }

}
