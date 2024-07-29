package cn.econets.blossom.module.crm.dal.mysql.business;


import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessProductDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Business Opportunity Products Mapper
 *
 */
@Mapper
public interface CrmBusinessProductMapper extends BaseMapperX<CrmBusinessProductDO> {

    // TODO @puhui999：Unused methods，Check if it has been deleted
    default void deleteByBusinessId(Long getBusinessId) { // TODO @lzxhqs：The first method，It is best to leave one line between the class and the class；
        delete(CrmBusinessProductDO::getBusinessId, getBusinessId);
    }

    default CrmBusinessProductDO selectByBusinessId(Long getBusinessId) {
        return selectOne(CrmBusinessProductDO::getBusinessId, getBusinessId);
    }

    default List<CrmBusinessProductDO> selectListByBusinessId(Long businessId) {
        // TODO @puhui999：Can be simplified，selectList(CrmBusinessProductDO::getBusinessId, businessId)
        return selectList(new LambdaQueryWrapperX<CrmBusinessProductDO>().eq(CrmBusinessProductDO::getBusinessId, businessId));
    }

}
