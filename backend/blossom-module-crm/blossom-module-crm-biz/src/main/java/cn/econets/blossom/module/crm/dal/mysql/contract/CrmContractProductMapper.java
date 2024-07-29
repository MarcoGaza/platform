package cn.econets.blossom.module.crm.dal.mysql.contract;


import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractProductDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Contract Product Mapper
 *
 */
@Mapper
public interface CrmContractProductMapper extends BaseMapperX<CrmContractProductDO> {

    // TODO @puhui999：Unused methods，Check if it has been deleted
    default void deleteByContractId(Long contractId) { // TODO @lzxhqs：The first method，It is best to leave one line between the class and the class；
        delete(CrmContractProductDO::getContractId, contractId);
    }

    default CrmContractProductDO selectByContractId(Long contractId) {
        return selectOne(CrmContractProductDO::getContractId, contractId);
    }

    default List<CrmContractProductDO> selectListByContractId(Long contractId) {
        return selectList(new LambdaQueryWrapperX<CrmContractProductDO>().eq(CrmContractProductDO::getContractId, contractId));
    }

}
