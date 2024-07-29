package cn.econets.blossom.module.crm.dal.mysql.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeQueryVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusTypeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Opportunity status type Mapper
 *
 */
@Mapper
public interface CrmBusinessStatusTypeMapper extends BaseMapperX<CrmBusinessStatusTypeDO> {

    default PageResult<CrmBusinessStatusTypeDO> selectPage(CrmBusinessStatusTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmBusinessStatusTypeDO>()
                .orderByDesc(CrmBusinessStatusTypeDO::getId));
    }

    default List<CrmBusinessStatusTypeDO> selectList(CrmBusinessStatusTypeQueryVO queryVO) {
        return selectList(new LambdaQueryWrapperX<CrmBusinessStatusTypeDO>()
                .eqIfPresent(CrmBusinessStatusTypeDO::getStatus, queryVO.getStatus())
                .inIfPresent(CrmBusinessStatusTypeDO::getId, queryVO.getIdList()));
    }

    // TODO @lzxhqs：This can be changed to selectByName。Based on business judgment id Match；This is more general；mapper Try to be as universal as possible，Not concerned with or particularly related to the business；
    /**
     * According toIDJapanesenameQuery
     *
     * @param id Opportunity status typeid
     * @param name Status type name
     * @return result
     */
    default CrmBusinessStatusTypeDO selectByIdAndName(Long id, String name) {
        return selectOne(CrmBusinessStatusTypeDO::getId, id, CrmBusinessStatusTypeDO::getName, name);
    }

}
