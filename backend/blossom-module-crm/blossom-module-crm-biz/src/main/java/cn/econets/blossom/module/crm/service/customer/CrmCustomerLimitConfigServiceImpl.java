package cn.econets.blossom.module.crm.service.customer;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerLimitConfigDO;
import cn.econets.blossom.module.crm.dal.mysql.customer.CrmCustomerLimitConfigMapper;
import cn.econets.blossom.module.crm.enums.customer.CrmCustomerLimitConfigTypeEnum;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.CUSTOMER_LIMIT_CONFIG_NOT_EXISTS;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

/**
 * Customer restriction configuration Service Implementation class
 *
 */
@Service
@Validated
public class CrmCustomerLimitConfigServiceImpl implements CrmCustomerLimitConfigService {

    @Resource
    private CrmCustomerLimitConfigMapper customerLimitConfigMapper;

    @Resource
    private DeptApi deptApi;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUB_TYPE, bizNo = "{{#limitId}}",
            success = CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUCCESS)
    public Long createCustomerLimitConfig(CrmCustomerLimitConfigSaveReqVO createReqVO) {
        validateUserAndDept(createReqVO.getUserIds(), createReqVO.getDeptIds());
        // Insert
        CrmCustomerLimitConfigDO limitConfig = BeanUtils.toBean(createReqVO, CrmCustomerLimitConfigDO.class);
        customerLimitConfigMapper.insert(limitConfig);

        // Record operation log context
        LogRecordContext.putVariable("limitType", CrmCustomerLimitConfigTypeEnum.getNameByType(limitConfig.getType()));
        LogRecordContext.putVariable("limitId", limitConfig.getId());
        return limitConfig.getId();
    }

    @Override
    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUCCESS)
    public void updateCustomerLimitConfig(CrmCustomerLimitConfigSaveReqVO updateReqVO) {
        // Check existence
        CrmCustomerLimitConfigDO oldLimitConfig = validateCustomerLimitConfigExists(updateReqVO.getId());
        validateUserAndDept(updateReqVO.getUserIds(), updateReqVO.getDeptIds());
        // Update
        CrmCustomerLimitConfigDO updateObj = BeanUtils.toBean(updateReqVO, CrmCustomerLimitConfigDO.class);
        customerLimitConfigMapper.updateById(updateObj);

        // Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldLimitConfig, CrmCustomerLimitConfigSaveReqVO.class));
    }

    @Override
    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUCCESS)
    public void deleteCustomerLimitConfig(Long id) {
        // Check existence
        CrmCustomerLimitConfigDO limitConfig = validateCustomerLimitConfigExists(id);
        // Delete
        customerLimitConfigMapper.deleteById(id);

        // Record operation log context
        LogRecordContext.putVariable("limitType", CrmCustomerLimitConfigTypeEnum.getNameByType(limitConfig.getType()));
    }

    @Override
    public CrmCustomerLimitConfigDO getCustomerLimitConfig(Long id) {
        return customerLimitConfigMapper.selectById(id);
    }

    @Override
    public PageResult<CrmCustomerLimitConfigDO> getCustomerLimitConfigPage(CrmCustomerLimitConfigPageReqVO pageReqVO) {
        return customerLimitConfigMapper.selectPage(pageReqVO);
    }

    private CrmCustomerLimitConfigDO validateCustomerLimitConfigExists(Long id) {
        CrmCustomerLimitConfigDO limitConfigDO = customerLimitConfigMapper.selectById(id);
        if (limitConfigDO == null) {
            throw exception(CUSTOMER_LIMIT_CONFIG_NOT_EXISTS);
        }
        return limitConfigDO;
    }

    /**
     * Verify the user and department of the input parameter
     *
     * @param userIds User ids
     * @param deptIds Department ids
     */
    private void validateUserAndDept(Collection<Long> userIds, Collection<Long> deptIds) {
        deptApi.validateDeptList(deptIds);
        adminUserApi.validateUserList(userIds);
    }

    @Override
    public List<CrmCustomerLimitConfigDO> getCustomerLimitConfigListByUserId(Integer type, Long userId) {
        AdminUserRespDTO user = adminUserApi.getUser(userId);
        Assert.notNull(user, "User({})Does not exist", userId);
        return customerLimitConfigMapper.selectListByTypeAndUserIdAndDeptId(type, userId, user.getDeptId());
    }

}
