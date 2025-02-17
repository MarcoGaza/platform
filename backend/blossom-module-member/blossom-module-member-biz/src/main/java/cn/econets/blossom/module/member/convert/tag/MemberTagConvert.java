package cn.econets.blossom.module.member.convert.tag;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagRespVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.tag.MemberTagDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Member Tag Convert
 *
 * 
 */
@Mapper
public interface MemberTagConvert {

    MemberTagConvert INSTANCE = Mappers.getMapper(MemberTagConvert.class);

    MemberTagDO convert(MemberTagCreateReqVO bean);

    MemberTagDO convert(MemberTagUpdateReqVO bean);

    MemberTagRespVO convert(MemberTagDO bean);

    List<MemberTagRespVO> convertList(List<MemberTagDO> list);

    PageResult<MemberTagRespVO> convertPage(PageResult<MemberTagDO> page);

}
