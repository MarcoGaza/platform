package cn.econets.blossom.module.crm.dal.mysql.product;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.util.CrmQueryWrapperUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * CRM Products Mapper
 *
 */
@Mapper
public interface CrmProductMapper extends BaseMapperX<CrmProductDO> {

    default PageResult<CrmProductDO> selectPage(CrmProductPageReqVO reqVO, Long userId) {
        MPJLambdaWrapperX<CrmProductDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_PRODUCT.getType(),
                CrmProductDO::getId, userId, null, Boolean.FALSE);
        // Splice your own query conditions
        query.selectAll(CrmProductDO.class)
                .likeIfPresent(CrmProductDO::getName, reqVO.getName())
                .eqIfPresent(CrmProductDO::getStatus, reqVO.getStatus())
                .orderByDesc(CrmProductDO::getId);
        return selectJoinPage(reqVO, CrmProductDO.class, query);
    }

    default CrmProductDO selectByNo(String no) {
        return selectOne(CrmProductDO::getNo, no);
    }

}
