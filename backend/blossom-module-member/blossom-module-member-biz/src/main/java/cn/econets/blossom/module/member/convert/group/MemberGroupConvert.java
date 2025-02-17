package cn.econets.blossom.module.member.convert.group;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupRespVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupSimpleRespVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * User Grouping Convert
 *
 * 
 */
@Mapper
public interface MemberGroupConvert {

    MemberGroupConvert INSTANCE = Mappers.getMapper(MemberGroupConvert.class);

    MemberGroupDO convert(MemberGroupCreateReqVO bean);

    MemberGroupDO convert(MemberGroupUpdateReqVO bean);

    MemberGroupRespVO convert(MemberGroupDO bean);

    List<MemberGroupRespVO> convertList(List<MemberGroupDO> list);

    PageResult<MemberGroupRespVO> convertPage(PageResult<MemberGroupDO> page);

    List<MemberGroupSimpleRespVO> convertSimpleList(List<MemberGroupDO> list);
}
