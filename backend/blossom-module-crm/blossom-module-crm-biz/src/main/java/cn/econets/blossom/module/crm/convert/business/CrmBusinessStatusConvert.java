package cn.econets.blossom.module.crm.convert.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusRespVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Opportunity Status Convert
 *
 */
@Mapper
public interface CrmBusinessStatusConvert {

    CrmBusinessStatusConvert INSTANCE = Mappers.getMapper(CrmBusinessStatusConvert.class);

    List<CrmBusinessStatusRespVO> convertList(List<CrmBusinessStatusDO> list);

    PageResult<CrmBusinessStatusRespVO> convertPage(PageResult<CrmBusinessStatusDO> page);

}
