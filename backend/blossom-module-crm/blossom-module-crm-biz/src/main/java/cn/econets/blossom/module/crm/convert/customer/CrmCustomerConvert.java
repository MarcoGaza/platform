package cn.econets.blossom.module.crm.convert.customer;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.CrmCustomerRespVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.CrmCustomerTransferReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionTransferReqBO;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.MapUtils.findAndThen;

/**
 * Customer Convert
 *
 */
@Mapper
public interface CrmCustomerConvert {

    CrmCustomerConvert INSTANCE = Mappers.getMapper(CrmCustomerConvert.class);

    default CrmCustomerRespVO convert(CrmCustomerDO customer, Map<Long, AdminUserRespDTO> userMap,
                                      Map<Long, DeptRespDTO> deptMap) {
        CrmCustomerRespVO customerResp = BeanUtils.toBean(customer, CrmCustomerRespVO.class);
        setUserInfo(customerResp, userMap, deptMap);
        return customerResp;
    }

    default PageResult<CrmCustomerRespVO> convertPage(PageResult<CrmCustomerDO> pageResult, Map<Long, AdminUserRespDTO> userMap,
                                                      Map<Long, DeptRespDTO> deptMap, Map<Long, Long> poolDayMap) {
        PageResult<CrmCustomerRespVO> result = BeanUtils.toBean(pageResult, CrmCustomerRespVO.class);
        result.getList().forEach(item -> {
            setUserInfo(item, userMap, deptMap);
            findAndThen(poolDayMap, item.getId(), item::setPoolDay);
        });
        return result;
    }

    /**
     * Set user information
     *
     * @param customer CRM Customer Response VO
     * @param userMap  User Information map
     * @param deptMap  User department information map
     */
    static void setUserInfo(CrmCustomerRespVO customer, Map<Long, AdminUserRespDTO> userMap, Map<Long, DeptRespDTO> deptMap) {
        customer.setAreaName(AreaUtils.format(customer.getAreaId()));
        findAndThen(userMap, customer.getOwnerUserId(), user -> {
            customer.setOwnerUserName(user.getNickname());
            findAndThen(deptMap, user.getDeptId(), dept -> customer.setOwnerUserDeptName(dept.getName()));
        });
        findAndThen(userMap, Long.parseLong(customer.getCreator()), user -> customer.setCreatorName(user.getNickname()));
    }

    @Mapping(target = "bizId", source = "reqVO.id")
    CrmPermissionTransferReqBO convert(CrmCustomerTransferReqVO reqVO, Long userId);

}
