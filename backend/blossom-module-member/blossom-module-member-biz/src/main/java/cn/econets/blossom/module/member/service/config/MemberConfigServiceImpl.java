package cn.econets.blossom.module.member.service.config;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import cn.econets.blossom.module.member.convert.config.MemberConfigConvert;
import cn.econets.blossom.module.member.dal.dataobject.config.MemberConfigDO;
import cn.econets.blossom.module.member.dal.mysql.config.MemberConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * Member Configuration Service Implementation class
 *
 *
 */
@Service
@Validated
public class MemberConfigServiceImpl implements MemberConfigService {

    @Resource
    private MemberConfigMapper memberConfigMapper;

    @Override
    public void saveConfig(MemberConfigSaveReqVO saveReqVO) {
        // Exists，Then update
        MemberConfigDO dbConfig = getConfig();
        if (dbConfig != null) {
            memberConfigMapper.updateById(MemberConfigConvert.INSTANCE.convert(saveReqVO).setId(dbConfig.getId()));
            return;
        }
        // Does not exist，Insert
        memberConfigMapper.insert(MemberConfigConvert.INSTANCE.convert(saveReqVO));
    }

    @Override
    public MemberConfigDO getConfig() {
        List<MemberConfigDO> list = memberConfigMapper.selectList();
        return CollectionUtils.getFirst(list);
    }

}
